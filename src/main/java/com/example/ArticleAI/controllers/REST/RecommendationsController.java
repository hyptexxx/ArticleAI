package com.example.ArticleAI.controllers.REST;

import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import com.example.ArticleAI.modules.recomendationsResolver.service.interfaces.IRecomendationsService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecommendationsController {

    private final IRecomendationsService recomendationsService;

    @GetMapping(value = "/api/recommendations")
    public ResponseEntity<Object> createRecommendation(@RequestParam("actualityPair") String actuality,
                                                       @RequestParam("articleId") Integer articleId) {
        try {
            Recommendation recommendation = recomendationsService.createRecomendation(actuality, articleId);
            if (recommendation != null) {
                return ResponseEntity.status(HttpStatus.OK).body(recommendation);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (JSONException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
