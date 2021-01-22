package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public interface FileProccesorEndpoint {

    @PostMapping(value = "/api/files/analyze")
    ResponseEntity<Object> processFiles(@RequestParam("file") List<MultipartFile> files, ArticleYake articleYake);
}