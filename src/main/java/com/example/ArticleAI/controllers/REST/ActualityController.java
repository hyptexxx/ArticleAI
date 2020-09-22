package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality.IActualityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActualityController {

    private final
    IActualityService actualityService;

    public ActualityController(IActualityService actualityService) {
        this.actualityService = actualityService;
    }


    @PostMapping(value = "/api/actuality/analyse")
    public ResponseEntity<Object> actualityAnalyse(@RequestParam("classes") String classes) {
        List<Actuality> actualityList =  actualityService.getActuality(classes);
        if (!actualityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(actualityList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
