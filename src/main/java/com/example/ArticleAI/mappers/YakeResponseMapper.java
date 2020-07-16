package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.YakeResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YakeResponseMapper implements org.springframework.jdbc.core.RowMapper<YakeResponse>  {
    @Override
    public YakeResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        YakeResponse yakeResponse = new YakeResponse();
        yakeResponse.setNgram(rs.getString("ngram"));
        yakeResponse.setScore(rs.getInt("score"));
        return yakeResponse;
    }
}
