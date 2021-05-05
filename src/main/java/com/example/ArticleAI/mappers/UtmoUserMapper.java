package com.example.ArticleAI.mappers;

import com.example.ArticleAI.dto.UtmoUserDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtmoUserMapper implements RowMapper<UtmoUserDto> {
    @Override
    public UtmoUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UtmoUserDto.builder()
                .remoteId(rs.getInt("id"))
                .fio(rs.getString("fio"))
                .info(rs.getString("info"))
                .remotePin(rs.getInt("remote_pin"))
                .userType(rs.getInt("user_type"))
                .build();
    }
}
