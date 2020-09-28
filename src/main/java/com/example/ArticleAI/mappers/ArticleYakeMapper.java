package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.ArticleYake;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleYakeMapper implements org.springframework.jdbc.core.RowMapper<ArticleYake> {
    @Override
    public ArticleYake mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArticleYake articleYake = new ArticleYake();
        articleYake.setText(rs.getString("text"));
        articleYake.setDeduplication_algo(rs.getString("deduplication_algo"));
        articleYake.setDeduplication_thresold(rs.getFloat("deduplication_thresold"));
        articleYake.setLanguage(rs.getString("language"));
        articleYake.setMax_ngram_size(rs.getFloat("max_ngram_size"));
        articleYake.setNumber_of_keywords(rs.getInt("number_of_keywords"));
        articleYake.setWindowSize(rs.getFloat("window_size"));
        return articleYake;
    }
}
