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

@RestController
public class YakeController {

    private final
    IYakeService yakeService;

    private final
    YakeDBService yakeDBService;

    private final
    IRequestService requestService;

    private final
    IPOIService poiService;

    private final
    Logger logger;

    private final
    IFileService fileService;


    public YakeController(IYakeService yakeService, YakeDBService yakeDBService, IRequestService requestService, IPOIService poiService, Logger logger, IFileService fileService) {
        this.yakeService = yakeService;
        this.yakeDBService = yakeDBService;
        this.requestService = requestService;
        this.poiService = poiService;
        this.logger = logger;
        this.fileService = fileService;
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
