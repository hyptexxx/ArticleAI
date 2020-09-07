package com.example.ArticleAI.controllers.REST;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class YandexXMLController {

    @GetMapping(value = "/api/yandex/search_count")
    public ResponseEntity<Object> analyseArticleText(@RequestParam("classes") String[] classes) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
