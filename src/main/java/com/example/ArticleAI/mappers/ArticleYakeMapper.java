package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.ArticleYake;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleYakeMapper implements org.springframework.jdbc.core.RowMapper<ArticleYake> {
    @Override
    public ArticleYake mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ArticleYake.builder()
                .text(rs.getString("text"))
                .deduplication_algo(rs.getString("deduplication_algo"))
                .deduplication_thresold(rs.getFloat("deduplication_thresold"))
                .language(rs.getString("language"))
                .max_ngram_size(rs.getFloat("max_ngram_size"))
                .number_of_keywords(rs.getInt("number_of_keywords"))
                .windowSize(rs.getFloat("window_size"))
                .build();
    }
}
