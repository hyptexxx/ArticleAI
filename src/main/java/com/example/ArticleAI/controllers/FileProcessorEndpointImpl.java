package com.example.ArticleAI.controllers;

import com.example.ArticleAI.configurations.filter.AllowedContentTypes;
import com.example.ArticleAI.configurations.filter.AllowedFile;
import com.example.ArticleAI.controllers.REST.FileProcessorEndpoint;
import com.example.ArticleAI.controllers.REST.NlpControllerService;
import com.example.ArticleAI.dto.FileHistoryDto;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.LoadedFile;
import com.example.ArticleAI.models.Recomendation;
import com.example.ArticleAI.modules.trainModule.FileProcessor;
import com.example.ArticleAI.output.PdfGeneratorService;
import com.example.ArticleAI.parser.YakeResponseParser;
import com.example.ArticleAI.repository.FileRepository;
import com.example.ArticleAI.repository.YakeRepository;
import com.example.ArticleAI.service.ApachePOI.POIService;
import com.example.ArticleAI.service.RequestService;
import com.example.ArticleAI.service.TextService;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileProcessorEndpointImpl implements FileProcessorEndpoint {

    private final POIService poiService;
    private final RequestService requestService;
    private final TextService iiTextService;
    private final PdfGeneratorService pdfGeneratorService;
    private final YakeRepository yakeRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final FileRepository fileRepository;

    private final FileProcessor fileProcessor;

    private final NlpControllerService nlpControllerService;

    @Override
    @SneakyThrows
    @SendToUser("/topic/analyseSteps")
    public ResponseEntity<Object> processFiles(MultipartFile file,
                                               ArticleYake articleYake) {
        final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        if (!file.isEmpty()) {
            Optional<LoadedFile> loadedFile = fileProcessor.saveFilesToFilesystem(getIfAllowed(file)
                    .orElseThrow(IllegalAccessError::new)
                    .getLoadedFile());

            ArticleYake parsedArticle = parseText(loadedFile, articleYake).get(0);
            yakeRepository.saveArticle(parsedArticle);
            messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", 1);
            messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", 2);
            messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", 3);
            messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", 4);

            Recomendation recomendation = nlpControllerService.actualityAnalyse(
                    YakeResponseParser.parse(requestService.sendRequest(parsedArticle))
                            .orElseThrow(IllegalArgumentException::new)
            );


            log.info("Получил рекомендации: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return ResponseEntity.status(HttpStatus.OK).body(recomendation);

        }

        log.error("Не удалось сформировать рекомендации: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }

    @Override
    public ResponseEntity<Resource> getDocumentById(HttpServletRequest request) throws IOException {
        final String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        final Integer publicationId = fileRepository.getFileIdByUserId(Integer.valueOf(userId));
        final String url = String.format("%s://%s:%d/api/file/%s",
                request.getScheme(), request.getServerName(), request.getServerPort(), publicationId);
        if (StringUtils.isNumeric(userId)) {
            final File file = pdfGeneratorService.generate(url, Integer.valueOf(userId), publicationId);
            final Integer certKey;
            try {
                certKey = fileRepository.saveCertificate(file.getAbsolutePath(), publicationId);
                fileRepository.updatePublication(publicationId, certKey);
            } catch (SQLIntegrityConstraintViolationException throwables) {
                log.info("certificate db is already exists userId: {}, row: {} ",
                        Integer.valueOf(userId), file.getAbsolutePath());
            }

            final Path path = Paths.get(file.getAbsolutePath());
            final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            final HttpHeaders header = new HttpHeaders();

            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + transliterate(file.getName()));

            log.info("{} Получил сертификат: {}", userId, transliterate(file.getName()));
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        }

        log.error("Не удалось выдать сертификат: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.badRequest()
                .build();
    }

    @Override
    public ResponseEntity<Resource> getFile(Integer publicationId) throws IOException {
        String filePath = fileRepository.getCertificateByFileId(publicationId);
        final File file = new File(filePath);
        final Path path = Paths.get(file.getAbsolutePath());
        final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        final HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + transliterate(file.getName()));
        log.info("{} выгрузил файл: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal(), transliterate(file.getName()));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @Override
    public ResponseEntity<Object> getFilesHistoryByUserId() {
        final String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (StringUtils.isNumeric(userId)) {
            final List<FileHistoryDto> fileHistories = fileRepository.getFilesHistoryByUserId(Integer.valueOf(userId));

            log.info("{} Получил историю загрузок", userId);
            return ResponseEntity.status(HttpStatus.OK).body(fileHistories);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @Override
    public ResponseEntity<Object> getFileByFileName(Integer fileId, String type) throws IOException {
        String filePath = null;
        if (type.equals("publication")) {
            filePath = fileRepository.getFilePathById(fileId);
        }

        if (type.equals("certificate")) {
            filePath = fileRepository.getCertificatePathById(fileId);
        }

        if (StringUtils.isBlank(filePath)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        final File file = new File(filePath);
        final Path path = Paths.get(file.getAbsolutePath());
        final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        final HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + transliterate(file.getName()));

        log.info("{} выгрузил файл: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal(), transliterate(file.getName()));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private Optional<AllowedFile> getIfAllowed(MultipartFile file) {
        Optional<AllowedContentTypes> allowedContentType;
        allowedContentType = Optional.ofNullable(AllowedContentTypes.fromDisplayString(file.getContentType()));

        if (allowedContentType.isPresent()) {
            switch (allowedContentType.get()) {
                case PDF:
                case DOCX:
                case DOC:
                case OCTET:
                    return Optional.ofNullable(AllowedFile.builder()
                            .isAllowed(Boolean.TRUE)
                            .loadedFile(LoadedFile.builder()
                                    .loadedFile(file)
                                    .type(allowedContentType.get())
                                    .build())
                            .build());
            }
        }

        log.info("{} недопустимый тип файла: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal(), file.getName());
        return Optional.empty();
    }

    private List<ArticleYake> parseText(Optional<LoadedFile> savedFiles, ArticleYake articleYake) {
        List<ArticleYake> result = new ArrayList<>();
        savedFiles.ifPresent(loadedFile -> {
            switch (loadedFile.getType()) {
                case PDF:
                    result.add(iiTextService.getYakeTextFromPDF(loadedFile.getLoadedFile(), articleYake));
                    break;
                case DOC:
                case DOCX:
                case OCTET:
                    result.add(poiService.getArticleYakeText(loadedFile.getSavedFile(), articleYake));
                    break;
            }
        });
        return result;
    }

    public static String transliterate(String message) {
        Transliterator toLatinTrans = Transliterator.getInstance("Cyrillic-Latin");
        String result = toLatinTrans.transliterate(message);
        return result.replaceAll("[*+?^${}()!<>@#,]", "");
    }
}





