package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.KeywordClass;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassEmbeddingMapper implements RowMapper<KeywordClass> {
    @Override
    public KeywordClass mapRow(ResultSet rs, int rowNum) throws SQLException {
        return KeywordClass.builder()
                .name(rs.getString("name"))
                .embedding(rs.getString("embedding")
                        .trim()
                        .replaceAll("\r\n", "")
                        .replaceAll("\\s", ""))
                .build();
    }
}
