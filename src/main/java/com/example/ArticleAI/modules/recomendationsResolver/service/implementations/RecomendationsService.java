package com.example.ArticleAI.modules.recomendationsResolver.service.implementations;

import com.example.ArticleAI.modules.recomendationsResolver.DAO.interfaces.IRecomendationsDAO;
import com.example.ArticleAI.modules.recomendationsResolver.exception.RecommendationCreationException;
import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import com.example.ArticleAI.modules.recomendationsResolver.service.interfaces.IRecomendationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecomendationsService implements IRecomendationsService {
    private static final int MAX_VALUE = 1;

    private final IRecomendationsDAO recomendationsDAO;
    private final NlpRequestService nlpService;

    @Override
    @Deprecated
    public Recommendation createRecomendation(String actuality, int articleId)
            throws RecommendationCreationException {
//        Recommendation recommendation;
//        List<Actuality> actualityPairs = new ActualityParser().getActualityFromJSON(actuality).stream()
//                .filter(parsedActuality -> parsedActuality.getActuality() > MAX_VALUE)
//                .collect(Collectors.toList());
//        recommendation = Recommendation.builder()
//                .recommendationText(nlpService.getRecommendations(new ArrayList<>()).stream()
//                        .map(Recommendation::getRecommendationText)
//                        .collect(Collectors.joining("\n")))
//                .build();
//
//        if (!StringUtils.isBlank(recommendation.getRecommendationText())) {
//            recomendationsDAO.save(recommendation, articleId);
//            return recommendation;
//        }
//
//        throw new RecommendationCreationException("can't get any recommendations");
        return null;
    }
}
