package com.example.ArticleAI.controllers;

import com.example.ArticleAI.DAO.interfaces.YakeRepository;
import com.example.ArticleAI.configurations.AllowedContentTypes;
import com.example.ArticleAI.controllers.REST.FileProcessorEndpoint;
import com.example.ArticleAI.dto.YakeDTO;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.FullArticle;
import com.example.ArticleAI.models.LoadedFile;
import com.example.ArticleAI.modules.trainModule.FileProcessor;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.IText.IITextService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileProcessorEndpointImpl implements FileProcessorEndpoint {

    private final IPOIService poiService;
    private final IRequestService requestService;
    private final IITextService iiTextService;
    private final IYakeService yakeService;
    private final YakeRepository yakeRepository;

    private final FileProcessor fileProcessor;

    private final Map<LoadedFile, Boolean> mappedSupportedFiles = new HashMap<>();
    private final Map<String, String> errorsToClient = new HashMap<>();


    @Override
    public ResponseEntity<Object> processFiles(List<MultipartFile> files, ArticleYake articleYake) throws IOException {
        Optional<List<LoadedFile>> savedFiles;
        Map<LoadedFile, Boolean> allowedFiles;

        if (!files.isEmpty()) {
            setSupportFiles(files);
            allowedFiles = getAllowedFiles();
            if (!allowedFiles.isEmpty()) {
                try {
                    savedFiles = fileProcessor.saveFilesToFilesystem(allowedFiles.keySet());
                    if (isTrainSet(files)) {

                    } else {
                        Integer generatedArticleId;
                        ArticleYake parsedArticle = parseText(savedFiles, articleYake);
                        Optional<Integer> generatedArticleIdOptional = yakeRepository.saveArticle(parsedArticle);
                        if (generatedArticleIdOptional.isPresent()) {
                            generatedArticleId = generatedArticleIdOptional.get();

                            return ResponseEntity.status(HttpStatus.OK).body(YakeDTO.builder()
                                    .generatedArticleId(generatedArticleId)
                                    .yakeResponse(yakeService.parseYakeResponseJSON(requestService.sendRequest(parsedArticle)))
                                    .build());
                        }
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    }
                } catch (FileAlreadyExistsException e) {
                    return ResponseEntity.status(HttpStatus.OK).body(null);
                }
            }
//
//
//            setSupportFiles(files);
//            allowedFiles = getAllowedFiles();
//            Optional<Map<FullArticle, LoadedFile>> response = Optional.empty();
//            Map<LoadedFile, ArticleYake> mergedText;
//            if (!allowedFiles.isEmpty()) {
//                try {
//                    savedFiles = fileProcessor.saveFilesToFilesystem(allowedFiles.keySet());
//                    mergedText = mapText(savedFiles, articleYake);
//                    response = sendPoolRequests(mergedText);
//                } catch (FileAlreadyExistsException e) {
//                    return ResponseEntity.status(HttpStatus.OK).body(sendPoolRequests(m.put(null, articleYake)));
//                }
//            }
//
//            return response.<ResponseEntity<Object>>map(fullArticleLoadedFileMap -> ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(fullArticleLoadedFileMap
//                            .keySet().stream()
//                            .findFirst()
//                            .get()
//                            .getSavedYakeResponse())).orElseGet(() -> ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(null));
//
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }


    private boolean isTrainSet(List<MultipartFile> files) {
        return files.size() > 1;
    }

    private Optional<Map<FullArticle, LoadedFile>> sendPoolRequests(Map<LoadedFile, ArticleYake> articlesYake) {
        Map<FullArticle, LoadedFile> result = new HashMap<>();
        articlesYake.forEach((loadedFile, articleYake) -> result.put(FullArticle.builder()
                .savedArticleYake(articleYake)
                .savedYakeResponse(yakeService.parseYakeResponseJSON(requestService.sendRequest(articleYake)))
                .build(), loadedFile));
        return Optional.of(result);
    }

    private void setSupportFiles(List<MultipartFile> files) {
        Optional<AllowedContentTypes> allowedContentType;
        this.mappedSupportedFiles.clear();

        for (MultipartFile file : files) {
            allowedContentType = Optional.ofNullable(AllowedContentTypes.fromDisplayString(file.getContentType()));

            if (allowedContentType.isPresent()) {
                switch (allowedContentType.get()) {
                    case PDF:
                    case DOCX:
                    case DOC:
                        this.mappedSupportedFiles.put(LoadedFile.builder()
                                .loadedFile(file)
                                .type(allowedContentType.get())
                                .build(), Boolean.TRUE);
                        break;
                }
            } else {
                this.mappedSupportedFiles.put(LoadedFile.builder()
                        .loadedFile(file)
                        .build(), Boolean.FALSE);
                this.errorsToClient.put(file.getOriginalFilename(), "Не поддерживаемый формат файла");
            }
        }
    }

    private Map<LoadedFile, Boolean> getAllowedFiles() {
        return mappedSupportedFiles.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .collect(Collectors.toMap(Map.Entry::getKey, multipartFileBooleanEntry -> true));
    }

    private Map<LoadedFile, ArticleYake> mapText(Optional<List<LoadedFile>> savedFiles, ArticleYake
            articleYake) {
        Map<LoadedFile, ArticleYake> result = new HashMap<>();
        if (savedFiles.isPresent()) {
            for (LoadedFile loadedFile : savedFiles.get()) {
                switch (loadedFile.getType()) {
                    case PDF:
                        result.put(loadedFile, iiTextService.getYakeTextFromPDF(loadedFile.getLoadedFile(), articleYake));
                        break;
                    case DOC:
                    case DOCX:
                        result.put(loadedFile, poiService.getArticleYakeText(loadedFile.getSavedFile(), articleYake));
                        break;
                }
            }
        }

        return result;
    }

    private ArticleYake parseText(Optional<List<LoadedFile>> savedFiles, ArticleYake articleYake) {
        ArticleYake result = null;
        if (savedFiles.isPresent()) {
            for (LoadedFile loadedFile : savedFiles.get()) {
                switch (loadedFile.getType()) {
                    case PDF:
                        result = iiTextService.getYakeTextFromPDF(loadedFile.getLoadedFile(), articleYake);
                        break;
                    case DOC:
                    case DOCX:
                        result = poiService.getArticleYakeText(loadedFile.getSavedFile(), articleYake);
                        break;
                }
            }
        }

        return result;
    }
}
