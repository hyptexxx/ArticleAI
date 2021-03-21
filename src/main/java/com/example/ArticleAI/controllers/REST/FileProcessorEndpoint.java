package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
public interface FileProcessorEndpoint {

    @PostMapping(value = "/api/files/analyze")
    @SendToUser("/topic/analyseSteps")
    @MessageMapping("/topic/analyseSteps")
    ResponseEntity<Object> processFiles(@RequestParam("files") MultipartFile files, ArticleYake articleYake)
            throws IOException, ParseException;
}