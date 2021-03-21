package com.example.ArticleAI.controllers;

import com.example.ArticleAI.configurations.filter.AllowedContentTypes;
import com.example.ArticleAI.configurations.filter.AllowedFile;
import com.example.ArticleAI.controllers.REST.FileProcessorEndpoint;
import com.example.ArticleAI.dto.YakeDTO;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.LoadedFile;
import com.example.ArticleAI.modules.trainModule.FileProcessor;
import com.example.ArticleAI.parser.YakeResponseParser;
import com.example.ArticleAI.repository.YakeRepository;
import com.example.ArticleAI.service.ApachePOI.POIService;
import com.example.ArticleAI.service.RequestService;
import com.example.ArticleAI.service.TextService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class FileProcessorEndpointImpl implements FileProcessorEndpoint {

    private final POIService poiService;
    private final RequestService requestService;
    private final TextService iiTextService;
    private final YakeRepository yakeRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    private final FileProcessor fileProcessor;


    @Override
    @SneakyThrows
    public ResponseEntity<Object> processFiles(MultipartFile file,
                                               ArticleYake articleYake) {
        final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        if (!file.isEmpty()) {
            try {
                Optional<LoadedFile> savedFile = fileProcessor.saveFilesToFilesystem(getIfAllowed(file)
                        .orElseThrow(IllegalAccessError::new)
                        .getLoadedFile());

                ArticleYake parsedArticle = parseText(savedFile, articleYake).get(0);
                Integer generatedArticleId = yakeRepository.saveArticle(parsedArticle)
                        .orElseThrow(IllegalAccessException::new);

                messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", "2");
                messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", "3");
                messagingTemplate.convertAndSendToUser(sessionId, "/topic/analyseSteps", "4");

                return ResponseEntity.status(HttpStatus.OK).body(YakeDTO.builder()
                        .generatedArticleId(generatedArticleId)
                        .yakeResponse(YakeResponseParser.parse(requestService.sendRequest(parsedArticle))
                                .orElseThrow(IllegalAccessError::new))
                        .build());

            } catch (FileAlreadyExistsException e) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
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
}







