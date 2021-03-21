package com.example.ArticleAI.repository;

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

    public List<String> getAllNgram() {
        return jdbcTemplate.queryForList("select ngram\n" +
                "from article_scores_yake\n" +
                "where article_id = -1 and\n" +
                "      article_scores_yake.ngram not like '%гбоу%' and\n" +
                "      article_scores_yake.ngram not like '%кафедр%' and\n" +
                "      article_scores_yake.ngram not like '%доцент%' and\n" +
                "      article_scores_yake.ngram not like '%профессор%' and\n" +
                "      article_scores_yake.ngram not like '%государственн%' and\n" +
                "      article_scores_yake.ngram not like '%общеобразовательн%' and\n" +
                "      article_scores_yake.ngram not like '%орлова%' and\n" +
                "      article_scores_yake.ngram not like '%наталья%' and\n" +
                "      article_scores_yake.ngram not like '%бюджетн%' and\n" +
                "      article_scores_yake.ngram not like '%учрежде%' and\n" +
                "      article_scores_yake.ngram not like '%бюджетн%' and\n" +
                "      article_scores_yake.ngram not like '%област%' and\n" +
                "      article_scores_yake.ngram not like '%москов%' and\n" +
                "      article_scores_yake.ngram not like '%москв%' and\n" +
                "      article_scores_yake.ngram not like '%автор%' and\n" +
                "      article_scores_yake.ngram not like '%высше%' and\n" +
                "      article_scores_yake.ngram not like '%кандидат%' and\n" +
                "      article_scores_yake.ngram not like '%университет%' and\n" +
                "      article_scores_yake.ngram not like '%вера%' and\n" +
                "      article_scores_yake.ngram not like '%ольга%' and\n" +
                "      article_scores_yake.ngram not like '%владислав%' and\n" +
                "      article_scores_yake.ngram not like '%статья%' and\n" +
                "      article_scores_yake.ngram not like '%.%' and\n" +
                "      article_scores_yake.ngram not like '%-%'", String.class);
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