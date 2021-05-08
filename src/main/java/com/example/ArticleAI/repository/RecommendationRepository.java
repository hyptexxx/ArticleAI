package com.example.ArticleAI.repository;

import com.example.ArticleAI.models.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Objects;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Recommendation recommendation, int userId) throws SQLIntegrityConstraintViolationException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        final String SQL = "insert into recommendations(user_id, actuality) values (?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setDouble(2, recommendation.getActuality());
                return ps;
            }, keyHolder);
        } catch (Exception e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            }
        }

        jdbcTemplate.batchUpdate("insert into hash_tags(recommendation_id, text) values (?, ?)",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, Objects.requireNonNull(keyHolder.getKey()).intValue());
                ps.setString(2, recommendation.getHasTags().get(i));
            }

            @Override
            public int getBatchSize() {
                return recommendation.getHasTags().size();
            }
        });

        jdbcTemplate.batchUpdate("insert into top_subject(recommendation_id, name, percent) values (?, ?, ?)",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, Objects.requireNonNull(keyHolder.getKey()).intValue());
                ps.setString(2, recommendation.getTopSubjects().get(i).getName());
                ps.setDouble(3, recommendation.getTopSubjects().get(i).getPercent());
            }

            @Override
            public int getBatchSize() {
                return recommendation.getTopSubjects().size();
            }
        });
    }

    public Double getRecommendationActualityByUserId(Integer userId) {
        return jdbcTemplate.queryForObject("select actuality from recommendations where user_id = ? order by id desc limit 1",
                Double.class, userId);
    }
}
