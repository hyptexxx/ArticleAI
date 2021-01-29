package com.example.ArticleAI.controllers;

import com.example.ArticleAI.configurations.AllowedContentTypes;
import com.example.ArticleAI.controllers.REST.FileProcessorEndpoint;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.FullArticle;
import com.example.ArticleAI.models.LoadedFile;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.modules.classesResolver.ClassesResolver;
import com.example.ArticleAI.modules.classesResolver.exceptions.emptyKeywordListException.EmptyKeywordListException;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.trainModule.FileProcessor;
import com.example.ArticleAI.service.implementations.DBService.YakeDBService;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import com.example.ArticleAI.service.interfaces.Classes.IClassesService;
import com.example.ArticleAI.service.interfaces.IText.IITextService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileProcessorEndpointImpl implements FileProcessorEndpoint {
    private final Logger logger;

    private final IFileService fileService;
    private final IPOIService poiService;
    private final IRequestService requestService;
    private final IITextService iiTextService;
    private final YakeDBService yakeDBService;
    private final IYakeService yakeService;
    private final IClassesService classesService;

    private final FileProcessor fileProcessor;
    private final ClassesResolver classesResolver;

    private final Map<LoadedFile, Boolean> mappedSupportedFiles = new HashMap<>();
    private final Map<String, String> errorsToClient = new HashMap<>();


    @Override
    public ResponseEntity<Object> processFiles(List<MultipartFile> files, ArticleYake articleYake) throws IOException {
        Optional<List<LoadedFile>> savedFiles;
        Map<LoadedFile, Boolean> allowedFiles;

        if (!files.isEmpty()) {
            setSupportFiles(files);
            allowedFiles = getAllowedFiles();
            Optional<Map<FullArticle, LoadedFile>> response = null;

            if (!allowedFiles.isEmpty()) {
                try {
                    savedFiles = fileProcessor.saveFilesToFilesystem(allowedFiles.keySet());
                    Map<LoadedFile, ArticleYake> mergedText = mapText(savedFiles, articleYake);
                    response = sendPoolRequests(mergedText);
                    saveResultResponse(response);
                } catch (FileAlreadyExistsException e) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorsToClient);
                }
            }

            return ResponseEntity.status(HttpStatus.OK).body(response.get()
                    .keySet().stream()
                    .findFirst()
                    .get()
                    .getSavedYakeResponse());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }

    private Optional<List<Integer>> saveResultResponse(Optional<Map<FullArticle, LoadedFile>> response) {
        List<Integer> generatedKeys = new ArrayList<>();

        if (response.isPresent()) {
            response.get().forEach((fullArticle, loadedFile) -> {
                List<Class> classes = null;
                classesResolver.setKeyWords(fullArticle.getSavedYakeResponse().stream()
                        .map(YakeResponse::getNgram)
                        .collect(Collectors.toList()));
                classesResolver.setArticleId(yakeDBService.saveAnalysedArticleToDB(loadedFile.getLoadedFile(),
                        fullArticle.getSavedArticleYake(),
                        fullArticle.getSavedYakeResponse(),
                        new ArrayList<>()));
                try {
                    classes = classesResolver.resolve();
                } catch (EmptyKeywordListException e) {
                    System.out.println(1);
                }
                generatedKeys.add(yakeDBService.saveAnalysedArticleToDB(loadedFile.getLoadedFile(),
                        fullArticle.getSavedArticleYake(),
                        fullArticle.getSavedYakeResponse(),
                        classes));
            });

            if (generatedKeys.isEmpty()) {
                logger.info("Failed to save Yake params");
                return Optional.empty();
            } else {
                logger.info("Yake params saved");
                return Optional.of(generatedKeys);
            }
        }

        return Optional.empty();
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

    private Map<LoadedFile, ArticleYake> mapText(Optional<List<LoadedFile>> savedFiles, ArticleYake articleYake) {
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
}
