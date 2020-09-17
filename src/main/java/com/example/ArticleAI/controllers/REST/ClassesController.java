package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.modules.classesResolver.ClassesResolver;
import com.example.ArticleAI.modules.classesResolver.exceptions.emptyKeywordListException.EmptyKeywordListException;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClassesController {

    private final
    IYakeService yakeService;

    private final
    ClassesResolver classesResolver;

    public ClassesController(IYakeService yakeService, ClassesResolver classesResolver) {
        this.yakeService = yakeService;
        this.classesResolver = classesResolver;
    }

    @PostMapping(value = "/api/actuality/analyse")
    public ResponseEntity<Object> actualityAnalyse(@RequestParam("analyseResponse") String response) throws IOException {
        List<String> keyWords = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        yakeService.parseYakeResponseJSON(response).forEach(yakeResponse -> {
            keyWords.add(yakeResponse.getNgram());
        });
        classesResolver.setKeyWords(keyWords);
        try {
            classes = classesResolver.resolve();
        } catch (EmptyKeywordListException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (classes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(classes);
    }
}