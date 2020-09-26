package com.example.ArticleAI.modules.recomendationsResolver.DAO.implementations;

import com.example.ArticleAI.modules.recomendationsResolver.DAO.interfaces.IRecomendationsDAO;
import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecomendationsDAO implements IRecomendationsDAO {


    private final
    JdbcTemplate jdbcTemplate;

    public RecomendationsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public boolean save(Recommendation recommendation, int articleId) {
        try {
            jdbcTemplate.update("insert into recommendations (article_id, text) values (?, ?)"
                    , articleId, recommendation.getRecommendationText());
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }
}
