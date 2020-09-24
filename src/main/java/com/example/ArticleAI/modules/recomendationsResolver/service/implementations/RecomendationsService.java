package com.example.ArticleAI.modules.recomendationsResolver.service.implementations;

import com.example.ArticleAI.modules.recomendationsResolver.DAO.interfaces.IRecomendationsDAO;
import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import com.example.ArticleAI.modules.recomendationsResolver.parser.ActualityParser;
import com.example.ArticleAI.modules.recomendationsResolver.service.interfaces.IRecomendationsService;
import org.springframework.stereotype.Service;

@Service
public class RecomendationsService implements IRecomendationsService {
    private static final int MAX_VALUE = 1;

    private final
    IRecomendationsDAO recomendationsDAO;

    public RecomendationsService(IRecomendationsDAO recomendationsDAO) {
        this.recomendationsDAO = recomendationsDAO;
    }

    @Override
    public Recommendation createRecomendation(String actuality, int articleId) {
        Recommendation recommendation = new Recommendation();
        new ActualityParser().getActualityFromJSON(actuality).forEach(parsedActuality -> {
            if (parsedActuality.getActuality() > MAX_VALUE) {
                recommendation.setRecommendationText("recommendation text");
            }
        });
        recomendationsDAO.save(recommendation, articleId);
        // todo need save recommendations?
        // todo remove -> 1
        // todo complete this algo
        return recommendation;
    }
}
