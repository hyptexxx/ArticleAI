package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class YandexXMLController {

    private final
    IRequestService requestService;

    public YandexXMLController(IRequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(value = "/api/yandex/search_count")
    public ResponseEntity<Object> analyseArticleText() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequestToYandex(""));
    }
}
