package com.example.ArticleAI.modules.classesResolver.mappers;

import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassMapper implements RowMapper<Class> {

    @Override
    public Class mapRow(ResultSet rs, int rowNum) throws SQLException {
        Class elem = new Class();
        elem.setClassId(rs.getInt("class_id"));
        elem.setClassName(rs.getString("class_name"));
        elem.setClassWeight(rs.getFloat("class_weight"));
        elem.setKeywordId(rs.getInt("keyword_id"));
        elem.setKeywordText(rs.getString("keyword_text"));
        return elem;
    }
}
