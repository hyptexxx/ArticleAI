package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.models.FullArticle;
import com.example.ArticleAI.service.implementations.DBService.YakeDBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataLoadController {

    private final
    YakeDBService  yakeDBService;

    public DataLoadController(YakeDBService yakeDBService) {
        this.yakeDBService = yakeDBService;
    }

    @PostMapping(value = "/api/yake/response")
    public ResponseEntity<Object> getSavedYakeResponse(@RequestParam("yakeId") Integer yakeId){
        final FullArticle fullArticle = new FullArticle(yakeDBService.getSavedYakeResponse(yakeId), yakeDBService.getSavedAnalysedArticle(yakeId));
        if (!fullArticle.getSavedYakeResponse().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(fullArticle);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
