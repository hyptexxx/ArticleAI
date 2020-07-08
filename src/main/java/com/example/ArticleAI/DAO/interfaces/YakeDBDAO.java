package com.example.ArticleAI.DAO.interfaces;

import com.example.ArticleAI.DAO.service.IYakeDBDAO;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YakeDBDAO implements IYakeDBDAO {

    private final
    JdbcTemplate jdbcTemplate;

    public YakeDBDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean saveAnalysedArticleToDB(ArticleYake articleYake, List<YakeResponse> yakeResponseList) {
        try {
            jdbcTemplate.update(
            "INSERT INTO" +
                    " article_yake " +
                    "(language, max_ngram_size, deduplication_thresold, deduplication_algo, windowSize, number_of_keywords, text) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?)",
                articleYake.getLanguage(),
                articleYake.getMax_ngram_size(),
                articleYake.getDeduplication_thresold(),
                articleYake.getDeduplication_algo(),
                articleYake.getWindowSize(),
                articleYake.getNumber_of_keywords(),
                articleYake.getText()
            );
        } catch (DataAccessException e){
            return false;
        }
        return true;
    }
}