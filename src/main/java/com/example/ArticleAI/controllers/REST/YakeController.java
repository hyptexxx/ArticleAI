package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.DAO.interfaces.YakeRepository;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.implementations.DBService.YakeDBService;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import com.example.ArticleAI.service.interfaces.ArticleFile.IFileService;
import com.example.ArticleAI.service.interfaces.Classes.IClassesService;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YakeController {

    private final IRequestService requestService;
    private final IYakeService yakeService;

    private final YakeRepository yakeRepository;


    @PostMapping(value = "/api/yake/analyze")
    public ResponseEntity<Object> analyseArticleText(ArticleYake articleYake) {
        if (requestService.sendRequest(articleYake).isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(requestService.sendRequest(null));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(requestService.sendRequest(articleYake));
        }
    }


    @PostMapping(value = "/api/yake/saveResultEntity")
    public ResponseEntity<Object> saveResultEntity(@RequestParam("generatedKey") Integer generatedKey,
                                                   @RequestParam("analyseResponse") String response) {
        if (yakeRepository.saveYakeScores(yakeService.parseYakeResponseJSON(response), generatedKey)) {
            log.info("Yake params saved");
            return ResponseEntity.status(HttpStatus.OK).body(generatedKey);
        }

        log.info("Failed to save Yake params");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}
