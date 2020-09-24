package com.example.ArticleAI.modules.recomendationsResolver.DAO.interfaces;

import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;

public interface IRecomendationsDAO {
    boolean save(Recommendation recommendation, int articleId);
}
