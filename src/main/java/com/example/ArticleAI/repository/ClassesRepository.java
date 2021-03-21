package com.example.ArticleAI.repository;

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
                .batchUpdate("update classes_embeddings " +
                        "set class_id = ?, embedding = ?, class_name = ?, actuality = ? where class_id = ?", new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, classesEmbeddings.get(i).getId());
                        ps.setString(2, classesEmbeddings.get(i).getEmbedding());
                        ps.setString(3, classesEmbeddings.get(i).getName());
                        ps.setLong(4, classesEmbeddings.get(i).getActuality());
                        ps.setInt(5, classesEmbeddings.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return classesEmbeddings.size();
                    }
                });
    }

    public Optional<Long> getActualityByClassName(String className) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("select actuality from classes_embeddings where class_name like ?", Long.class, className));
    }
}
