package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.controllers.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.controllers.service.interfaces.ArticleFile.IFileService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RESTFileController {

    private final
    Logger logger;

    private final
    IFileService fileService;

    private final
    IPOIService poiService;

    public RESTFileController(Logger logger, IFileService fileService, IPOIService poiService) {
        this.logger = logger;
        this.fileService = fileService;
        this.poiService = poiService;
    }

    @PostMapping(value = "/api/files/analyze")
    public ResponseEntity<Object> saveFile(@RequestParam("file") MultipartFile file) {
        if(fileService.saveFileToFilesystem(file)){
            logger.info("file saved");

        } else {
            logger.info("failed to save file");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
