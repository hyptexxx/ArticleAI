package com.example.ArticleAI.repository;

import com.example.ArticleAI.mappers.ArticleYakeMapper;
import com.example.ArticleAI.mappers.YakeResponseMapper;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YakeRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer saveArticle(ArticleYake articleYake, Integer userId, Integer fileId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO articles(user_id," +
                                        " prepared_text," +
                                        " file_id) VALUES (?,?,?)",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );

                if (userId != null) {
                    ps.setInt(1, userId);
                } else {
                    ps.setNull(1, Types.INTEGER);
                }

                ps.setString(2, articleYake.getText());
                ps.setInt(3, fileId);
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
               return getArticleByFileId(fileId);
            }
        }

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
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

    public Integer getArticleByFileId(Integer fileId) {
        return jdbcTemplate.queryForObject("select id from articles where file_id = ?", Integer.class, fileId);
    }

    public void updateArticle(Integer recommendationId, Integer articleId) {
        jdbcTemplate.update("update articles set recommendation_id = ? where id = ?",
                recommendationId, articleId);
    }

    public Integer saveYakeParams(ArticleYake articleYake, Integer articleId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO yake_params(language,  " +
                                        "max_ngram_size, " +
                                        " deduplication_thresold, " +
                                        " deduplication_algo, " +
                                        " window_size," +
                                        "  number_of_keywords, " +
                                        " article_id) VALUES (?,?,?,?,?,?,?)",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
                ps.setString(1, articleYake.getLanguage());
                ps.setFloat(2, articleYake.getMax_ngram_size());
                ps.setFloat(3, articleYake.getDeduplication_thresold());
                ps.setString(4, articleYake.getDeduplication_algo());
                ps.setFloat(5, articleYake.getWindowSize());
                ps.setInt(6, articleYake.getNumber_of_keywords());
                ps.setInt(7, articleId);
                return ps;
            }, keyHolder);

        } catch (DataAccessException e) {
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                log.info("Не удалось сохранить параметры YAKE user: {}, error: {}",
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        e.getMessage());
            }
        }

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}