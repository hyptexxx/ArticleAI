package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    public RESTFileController(Logger logger, IFileService fileService, IPOIService poiService, IRequestService requestService) {
        this.logger = logger;
        this.fileService = fileService;
        this.poiService = poiService;
        this.requestService = requestService;
    }

    @PostMapping(value = "/api/files/analyze")
    public ResponseEntity<Object> saveFile(@RequestParam("file") MultipartFile file, ArticleYake articleYake) throws IOException {
        if (fileService.saveFileToFilesystem(file)) {
            logger.info("file saved");
            if (requestService.sendRequest(articleYake).isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(articleYake));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
            }
        } else {
            logger.info("failed to save file");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
