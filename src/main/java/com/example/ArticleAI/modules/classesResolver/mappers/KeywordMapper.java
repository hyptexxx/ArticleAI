package com.example.ArticleAI.modules.classesResolver.mappers;

import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeywordMapper implements RowMapper<Keyword> {
    @Override
    public Keyword mapRow(ResultSet rs, int rowNum) throws SQLException {
        Keyword keyword = new Keyword();
        keyword.setKeywordId(rs.getInt("id"));
        keyword.setKeywordText(rs.getString("keyword_text"));
        return keyword;
    }
}
