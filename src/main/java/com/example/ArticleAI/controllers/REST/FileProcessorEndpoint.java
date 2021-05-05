package com.example.ArticleAI.controllers.REST;


import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.output.PdfGeneratorService;
import com.example.ArticleAI.schedule.SearchScheduler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

@RestController
public interface FileProcessorEndpoint {

    @PostMapping(value = "/api/files/analyze")
    @SendToUser("/topic/analyseSteps")
    @MessageMapping("/topic/analyseSteps")
    ResponseEntity<Object> processFiles(@RequestParam("files") MultipartFile files, ArticleYake articleYake)
            throws IOException, ParseException;

    @GetMapping("/api/document")
    ResponseEntity<Resource> getDocumentById(HttpServletRequest request) throws IOException;

    @GetMapping("/api/file/{userId}")
    ResponseEntity<Resource> getFile(@PathVariable("userId") Integer userId) throws IOException;

    @PostMapping("/api/fileHistory")
    ResponseEntity<Object> getFilesHistoryByUserId() throws IOException;

    @GetMapping("/api/file/{fileId}/{type}")
    ResponseEntity<Object> getFileByFileName(@PathVariable("fileId") Integer fileId,
                                             @PathVariable("type") String type) throws IOException;
}