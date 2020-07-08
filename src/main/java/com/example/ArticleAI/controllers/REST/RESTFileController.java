package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.implementations.DBService.YakeDBService;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

@RestController
public class RESTFileController {

    private final
    Logger logger;

    private final
    IFileService fileService;

    private final
    IPOIService poiService;

    private final
    IRequestService requestService;

    private final
    IYakeService yakeService;

    private final
    YakeDBService yakeDBService;

    public RESTFileController(Logger logger, IFileService fileService, IPOIService poiService, IRequestService requestService, IYakeService yakeService, YakeDBService yakeDBService) {
        this.logger = logger;
        this.fileService = fileService;
        this.poiService = poiService;
        this.requestService = requestService;
        this.yakeService = yakeService;
        this.yakeDBService = yakeDBService;
    }

    @PostMapping(value = "/api/files/analyze")
    public ResponseEntity<Object> saveFile(@RequestParam("file") MultipartFile file, ArticleYake articleYake) throws IOException {
        try{
            if (fileService.saveFileToFilesystem(file)) {
                logger.info("File saved:\t" + fileService.getFile().getAbsolutePath());
                if (requestService.sendRequest(poiService.getArticleYakeText(fileService.getFile(), articleYake)).isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
                }
            } else {
                logger.info("Failed to save file");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }
        } catch (FileAlreadyExistsException e){
            logger.info(e.getMessage() + "\t" + fileService.getFile().getAbsolutePath());
            if (requestService.sendRequest(poiService.getArticleYakeText(fileService.getFile(), articleYake)).isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
            }
        }
    }

    @PostMapping(value = "/api/yake/analyze")
    public ResponseEntity<Object> analyseArticleText(ArticleYake articleYake) throws IOException {
        if (requestService.sendRequest(articleYake).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
        }
    }

    @PostMapping(value = "/api/yake/saveResultEntity")
    public ResponseEntity<Object> saveResultEntity(@RequestParam("file") MultipartFile file,
                                                   ArticleYake articleYake,
                                                   @RequestParam("analyseResponse") String response) throws IOException {
        if(yakeDBService.saveAnalysedArticleToDB(file, poiService.getArticleYakeText(fileService.getFile(), articleYake), yakeService.parseYakeResponseJSON(response))){
            logger.info("Yake params saved");
            return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
        } else {
            logger.info("Failed to save Yake params");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
        }
    }
}