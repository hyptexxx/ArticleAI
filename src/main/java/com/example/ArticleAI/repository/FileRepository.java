package com.example.ArticleAI.repository;

import com.example.ArticleAI.dto.FileHistoryDto;
import com.example.ArticleAI.mappers.FileHistoryDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileRepository {
    private final JdbcTemplate jdbcTemplate;

    public void save(String path, Integer userId) throws SQLIntegrityConstraintViolationException {
        try {
            jdbcTemplate.update("insert into files(user_id, file_path, date) values (?, ?, current_date)",
                    userId, path);
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            }
        }
    }

    public String getCertificateByFileId(Integer publicationId) {
        return jdbcTemplate.queryForObject("select files.file_path from files\n" +
                        "inner join certificates c on files.certificate_id = c.id\n" +
                        "where files.id = ?",
                String.class, publicationId);
    }

    public Integer getFileIdByUserId(Integer userId) {
        return jdbcTemplate.queryForObject("select id from files where user_id = ? order by id desc limit 1",
                Integer.class, userId);
    }

    public Integer saveCertificate(String path, Integer publicationId) throws SQLIntegrityConstraintViolationException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String SQL = "insert into certificates(file_path, file_id, date) values (?, ?, current_date)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, path);
                ps.setInt(2, publicationId);
                return ps;
            }, keyHolder);

            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            }
        }

        return null;
    }

    public void updatePublication(Integer publicationId, Integer certKey) {
        jdbcTemplate.update("update files set certificate_id = ? where id = ?",
                certKey, publicationId);
    }

    public List<FileHistoryDto> getFilesHistoryByUserId(Integer userId) {
        return jdbcTemplate.query("select certificates.file_path as certificate_path,\n" +
                        "       files.file_path        as publication_path,\n" +
                        "       certificates.date      as certificate_generation_date,\n" +
                        "       files.date             as publication_upload_date,\n" +
                        "       users.fio              as fio,\n" +
                        "       files.id               as publication_id,\n" +
                        "       certificates.id        as certificate_id\n" +
                        "from users\n" +
                        "         inner join files on files.user_id = users.id\n" +
                        "         inner join certificates on files.certificate_id = certificates.id\n" +
                        "where users.id = ?",
               new FileHistoryDtoMapper(), userId);
    }

    public String getFilePathById (Integer fileId) {
        return jdbcTemplate.queryForObject("select file_path from files where id = ?", String.class, fileId);
    }

    public String getCertificatePathById (Integer fileId) {
        return jdbcTemplate.queryForObject("select file_path from certificates where id = ?", String.class, fileId);
    }
}
