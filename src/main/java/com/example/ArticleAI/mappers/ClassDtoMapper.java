package com.example.ArticleAI.mappers;

import com.example.ArticleAI.dto.ClassDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassDtoMapper implements RowMapper<ClassDto> {
    @Override
    public ClassDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClassDto.builder()
                .classId(rs.getInt("id"))
                .className(rs.getString("name"))
                .build();
    }
}
