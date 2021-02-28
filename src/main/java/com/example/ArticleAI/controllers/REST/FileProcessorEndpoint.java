package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
public interface FileProcessorEndpoint {

    @PostMapping(value = "/api/files/analyze")
    ResponseEntity<Object> processFiles(@RequestParam("files") List<MultipartFile> files, ArticleYake articleYake)
            throws IOException, ParseException;
}