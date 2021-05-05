package com.example.ArticleAI.repository;

import com.example.ArticleAI.dto.ClassDto;
import com.example.ArticleAI.mappers.ClassDtoMapper;
import com.example.ArticleAI.mappers.ClassEmbeddingMapper;
import com.example.ArticleAI.models.ClassesEmbeddings;
import com.example.ArticleAI.models.KeywordClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassesRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<KeywordClass> getAllClassesEmbeddings() {
        List<KeywordClass> result;
        result = jdbcTemplate
                .query("select embedding, class_name from classes_embeddings", new ClassEmbeddingMapper());
        return result;
    }

    public List<String> getAllClassesNames() {
        return jdbcTemplate.queryForList("select class_name from classes_embeddings", String.class);
    }

    public List<ClassesEmbeddings> getAll() {
        return jdbcTemplate
                .query("select * from classes_embeddings", (rs, rowNum) -> ClassesEmbeddings.builder()
                        .id(rs.getInt("class_id"))
                        .name(rs.getString("class_name"))
                        .embedding(rs.getNString("embedding"))
                        .build());
    }

    public int[] saveOrUpdate(List<ClassesEmbeddings> classesEmbeddings) {
        return jdbcTemplate

                .batchUpdate("insert into class_actuality (id, actuality, class_id, date) values (0, ?, ?, current_date )", new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setDouble(1, classesEmbeddings.get(i).getActuality());
                        ps.setInt(2, classesEmbeddings.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return classesEmbeddings.size();
                    }
                });
    }

    public Optional<Double> getActualityByClassName(String className) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("select class_actuality.actuality from class_actuality\n" +
                        "inner join classes_embeddings ce on class_actuality.class_id = ce.class_id\n" +
                        "where class_name like ?\n" +
                        "order by date desc limit 1", Double.class, className));
    }

    public List<ClassDto> getAllDto() {
        return jdbcTemplate.query("select class_id, class_name from classes_embeddings", new ClassDtoMapper());
    }
}
