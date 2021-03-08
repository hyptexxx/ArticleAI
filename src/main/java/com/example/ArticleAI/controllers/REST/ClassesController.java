package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.DAO.interfaces.YakeRepository;
import com.example.ArticleAI.dto.ActualityDTO;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.actualityResolver.service.implementation.Actuality.ActualityService;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality.IActualityService;
import com.example.ArticleAI.modules.classesResolver.ClassesResolver;
import com.example.ArticleAI.modules.classesResolver.exceptions.emptyKeywordListException.EmptyKeywordListException;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassesController {

    private final IYakeService yakeService;
    private final ActualityService actualityService;

    private final ClassesResolver classesResolver;

    private final YakeRepository yakeRepository;

    @PostMapping(value = "/api/classes/analyse")
    public ResponseEntity<Object> actualityAnalyse(@RequestParam("analyseResponse") String response,
                                                   @RequestParam("articleId") Integer articleId) {

        if (yakeRepository.saveYakeScores(yakeService.parseYakeResponseJSON(response), articleId)) {
            log.info("Yake params saved");

            List<String> keyWords = new ArrayList<>();
            List<Class> classes;
            List<ActualityDTO> actualities;

            yakeService.parseYakeResponseJSON(response)
                    .forEach(yakeResponse -> keyWords.add(yakeResponse.getNgram()));

            classesResolver.setKeyWords(keyWords);
            classesResolver.setArticleId(articleId);

            try {
                classes = classesResolver.resolve();
            } catch (EmptyKeywordListException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (classes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

//            actualities = actualityService.getActuality(new ArrayList<>());

            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        log.info("Failed to save Yake params");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
