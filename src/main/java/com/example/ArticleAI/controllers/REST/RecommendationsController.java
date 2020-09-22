package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import com.example.ArticleAI.modules.recomendationsResolver.service.interfaces.IRecomendationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationsController {

    private final
    IRecomendationsService recomendationsService;

    public RecommendationsController(IRecomendationsService requestService) {
        this.recomendationsService = requestService;
    }

    @GetMapping(value = "/api/recommendations")
    public ResponseEntity<Object> createRecommendation(@RequestParam("actualityPair") String actuality) {
        Recommendation recommendation = recomendationsService.createRecomendation(actuality);
        if (recommendation != null) {
            return ResponseEntity.status(HttpStatus.OK).body(recommendation);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
