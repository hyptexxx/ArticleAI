package com.example.ArticleAI.modules.recomendationsResolver.service.interfaces;

import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;

public interface IRecomendationsService {
    Recommendation createRecomendation(String actuality, int articleId);
}
