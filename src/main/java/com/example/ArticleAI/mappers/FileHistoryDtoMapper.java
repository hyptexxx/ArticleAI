package com.example.ArticleAI.mappers;

import com.example.ArticleAI.dto.FileHistoryDto;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHistoryDtoMapper implements RowMapper<FileHistoryDto> {
    @Override
    public FileHistoryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FileHistoryDto.builder()
                .certificateGenerationDate(formateDate(rs.getDate("certificate_generation_date")))
                .publicationUploadDate(formateDate(rs.getDate("publication_upload_date")))
                .certificateName(getFileName(rs.getString("certificate_path")))
                .publicationName(getFileName(rs.getString("publication_path")))
                .publicationId(rs.getInt("publication_id"))
                .certificateId(rs.getInt("certificate_id"))
                .fio(rs.getString("fio"))
                .actuality(rs.getDouble("actuality"))
                .build();
    }

    private static String formateDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    private static String getFileName(String filePath) {
        return new File(filePath).getName();
    }
}
