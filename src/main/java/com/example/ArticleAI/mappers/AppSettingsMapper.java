package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.AppSettings;
import com.example.ArticleAI.models.ArticleYake;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppSettingsMapper implements RowMapper<AppSettings> {
    @Override
    public AppSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AppSettings.builder()
                .yakeParams(ArticleYake.builder()
                        .deduplication_algo(rs.getString("deduplication_algo"))
                        .deduplication_thresold(rs.getFloat("deduplication_thresold"))
                        .language(rs.getString("language"))
                        .max_ngram_size(rs.getFloat("max_ngram_size"))
                        .number_of_keywords(rs.getInt("number_of_keywords"))
                        .windowSize(rs.getFloat("window_size"))
                        .build())
                .coefficient(rs.getDouble("coefficient"))
                .build();
    }
}




