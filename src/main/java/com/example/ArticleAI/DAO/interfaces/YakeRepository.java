package com.example.ArticleAI.DAO.interfaces;

import com.example.ArticleAI.mappers.ArticleYakeMapper;
import com.example.ArticleAI.mappers.YakeResponseMapper;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YakeRepository {

    private final
    JdbcTemplate jdbcTemplate;

    public Integer saveAnalysedArticleToDB(ArticleYake articleYake, List<YakeResponse> yakeResponseList) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO article(id," +
                                        " language," +
                                        " max_ngram_size," +
                                        " deduplication_thresold," +
                                        " deduplication_algo," +
                                        " window_size," +
                                        " number_of_keywords," +
                                        " prepared_text) VALUES (?,?,?,?,?,?,?,?)",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
                ps.setInt(1, 0);
                ps.setString(2, articleYake.getLanguage());
                ps.setFloat(3, articleYake.getMax_ngram_size());
                ps.setFloat(4, articleYake.getDeduplication_thresold());
                ps.setString(5, articleYake.getDeduplication_algo());
                ps.setFloat(6, articleYake.getWindowSize());
                ps.setInt(7, articleYake.getNumber_of_keywords());
                ps.setString(8, articleYake.getText());
                return ps;
            }, keyHolder);

            for (YakeResponse yakeResponse : yakeResponseList) {
                jdbcTemplate.update("insert into article_scores_yake(article_id, ngram, score) values (?,?,?)",
                        keyHolder.getKey(),
                        yakeResponse.getNgram(),
                        yakeResponse.getScore());
            }
        } catch (DataAccessException e) {
            System.out.println(e);
        }
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public Optional<Integer> saveArticle(ArticleYake articleYake) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO article(id," +
                                        " language," +
                                        " max_ngram_size," +
                                        " deduplication_thresold," +
                                        " deduplication_algo," +
                                        " window_size," +
                                        " number_of_keywords," +
                                        " prepared_text) VALUES (?,?,?,?,?,?,?,?)",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
                ps.setInt(1, 0);
                ps.setString(2, articleYake.getLanguage());
                ps.setFloat(3, articleYake.getMax_ngram_size());
                ps.setFloat(4, articleYake.getDeduplication_thresold());
                ps.setString(5, articleYake.getDeduplication_algo());
                ps.setFloat(6, articleYake.getWindowSize());
                ps.setInt(7, articleYake.getNumber_of_keywords());
                ps.setString(8, articleYake.getText());
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return Optional.of(keyHolder.getKey().intValue());
    }

    public boolean saveYakeScores(List<YakeResponse> yakeResponseList, Integer key) {
        try {
            for (YakeResponse yakeResponse : yakeResponseList) {
                jdbcTemplate.update("insert into article_scores_yake(article_id, ngram, score) values (?,?,?)",
                        key, yakeResponse.getNgram(), yakeResponse.getScore());
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public List<YakeResponse> getSavedYakeResponse(Integer yakeId) {
        return jdbcTemplate.query("select * from article_scores_yake where article_id = ?",
                new YakeResponseMapper(), yakeId);
    }

    public ArticleYake getSavedAnalysedArticle(Integer yakeId) {
        return jdbcTemplate.queryForObject("select * from article_params where article_id = ?",
                new ArticleYakeMapper(), yakeId);
    }
}