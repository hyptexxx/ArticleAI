package com.example.ArticleAI.controllers;

import com.example.ArticleAI.configurations.AllowedContentTypes;
import com.example.ArticleAI.controllers.REST.FileProccesorEndpoint;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.LoadedFile;
import com.example.ArticleAI.modules.trainModule.FileProcessor;
import com.example.ArticleAI.service.implementations.DBService.YakeDBService;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import com.example.ArticleAI.service.interfaces.Classes.IClassesService;
import com.example.ArticleAI.service.interfaces.IText.IITextService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FileProcessorEndpointImpl implements FileProccesorEndpoint {
    private final Logger logger;

    private final IFileService fileService;
    private final IPOIService poiService;
    private final IRequestService requestService;
    private final IITextService iiTextService;
    private final YakeDBService yakeDBService;
    private final IYakeService yakeService;
    private final IClassesService classesService;

    private final FileProcessor fileProcessor;

    private final Map<LoadedFile, Boolean> mappedSupportedFiles = new HashMap<>();
    private final Map<String, String> errorsToClient = new HashMap<>();

    public FileProcessorEndpointImpl(Logger logger, IFileService fileService, IPOIService poiService,
                                     IRequestService requestService, IITextService iiTextService,
                                     YakeDBService yakeDBService, IYakeService yakeService, IClassesService classesService,
                                     FileProcessor fileProcessor) {
        this.logger = logger;
        this.fileService = fileService;
        this.poiService = poiService;
        this.requestService = requestService;
        this.iiTextService = iiTextService;
        this.yakeDBService = yakeDBService;
        this.yakeService = yakeService;
        this.classesService = classesService;
        this.fileProcessor = fileProcessor;
    }


    @Override
    public ResponseEntity<Object> processFiles(List<MultipartFile> files, ArticleYake articleYake) {
        Optional<List<LoadedFile>> savedFiles;
        Map<LoadedFile, Boolean> allowedFiles;

        if (!files.isEmpty()) {
            setSupportFiles(files);
            allowedFiles = getAllowedFiles();

            if (!allowedFiles.isEmpty()) {
                try {
                    savedFiles = fileProcessor.saveFilesToFilesystem(allowedFiles.keySet());
                    if (savedFiles.isPresent()) {
                        sendPoolRequests(getText(savedFiles, articleYake));
                    }
                    System.out.println(1);
                } catch (FileAlreadyExistsException e) {
                    return ResponseEntity
                            .status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(errorsToClient);
                }
            }

            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(errorsToClient);
        }

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(null);

    }

//    private List<Integer> saveResultResponse(List<String> sendPoolRequests) {
//        List<Integer> generatedKeys = new ArrayList<>();
//
//        Integer generated_key = yakeDBService.saveAnalysedArticleToDB(file, poiService.getArticleYakeText(fileService.getFile(), articleYake),
//                yakeService.parseYakeResponseJSON(response),
//                classesService.parseClasses(classes));
//        if (generated_key >= 0) {
//            logger.info("Yake params saved");
//            return ResponseEntity.status(HttpStatus.OK).body(generated_key);
//        } else {
//            logger.info("Failed to save Yake params");
//            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
//        }
//    }

    private List<String> sendPoolRequests(List<ArticleYake> articlesYake) {
        return articlesYake.stream()
                .map(requestService::sendRequest)
                .collect(Collectors.toList());
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

    private List<ArticleYake> getText(Optional<List<LoadedFile>> savedFiles, ArticleYake articleYake) {
        List<ArticleYake> result = new ArrayList<>();

        for (LoadedFile loadedFile : savedFiles.get()) {
            switch (loadedFile.getType()) {
                case PDF:
                    ArticleYake qwee = iiTextService.getYakeTextFromPDF(loadedFile.getLoadedFile(), articleYake);
                    result.add(qwee);
                    break;
                case DOC:
                case DOCX:
                    ArticleYake qwe = poiService.getArticleYakeText(loadedFile.getSavedFile(), articleYake);
                    result.add(qwe);
                    break;
            }
        }

        return result;
    }
}
