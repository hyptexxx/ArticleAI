package com.example.ArticleAI.mappers;

import com.example.ArticleAI.dto.ActualityStatsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActualityStatsDtoMapper implements RowMapper<ActualityStatsDto> {
    @Override
    public ActualityStatsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ActualityStatsDto.builder()
                .actuality(rs.getDouble("actuality"))
                .classId(rs.getInt("class_id"))
                .className(rs.getString("name"))
                .date(formatDate(rs.getDate("date")))
                .build();
    }

    private static String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
