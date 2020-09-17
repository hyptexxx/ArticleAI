package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import com.example.ArticleAI.service.interfaces.IText.IITextService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

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
    IITextService iiTextService;


    public RESTFileController(Logger logger, IFileService fileService, IPOIService poiService, IRequestService requestService, IITextService iiTextService) {
        this.logger = logger;
        this.fileService = fileService;
        this.poiService = poiService;
        this.requestService = requestService;
        this.iiTextService = iiTextService;
    }

    @PostMapping(value = "/api/files/analyze")
    public ResponseEntity<Object> saveFile(@RequestParam("file") MultipartFile file, ArticleYake articleYake) throws IOException {
        ArticleYake articleText = new ArticleYake();
        try {
            if (fileService.saveFileToFilesystem(file)) {
                switch (Objects.requireNonNull(file.getContentType())) {
                    case "application/pdf":
                        articleText = iiTextService.getYakeTextFromPDF(file, articleYake);
                        break;
                    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    case "application/msword":
                        articleText = poiService.getArticleYakeText(fileService.getFile(), articleYake);
                        break;
                }
                logger.info("File saved:\t" + fileService.getFile().getAbsolutePath());
                if (requestService.sendRequest(articleText).isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
                }
            } else {
                logger.info("Failed to save file");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }
        } catch (FileAlreadyExistsException e) {
            logger.info(e.getMessage() + "\t" + fileService.getFile().getAbsolutePath());
            if (requestService.sendRequest(articleText).isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
            }
        }
    }


}