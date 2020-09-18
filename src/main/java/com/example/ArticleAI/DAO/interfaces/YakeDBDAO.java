package com.example.ArticleAI.DAO.interfaces;

import com.example.ArticleAI.DAO.service.IYakeDBDAO;
import com.example.ArticleAI.mappers.ArticleYakeMapper;
import com.example.ArticleAI.mappers.YakeResponseMapper;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class YakeDBDAO implements IYakeDBDAO {

    private final
    JdbcTemplate jdbcTemplate;

    public YakeDBDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean saveAnalysedArticleToDB(ArticleYake articleYake, List<YakeResponse> yakeResponseList, List<Class> classes) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO article(article_id) VALUES (?)",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
                ps.setInt(1, 0);
                return ps;
            }, keyHolder);

            for (YakeResponse yakeResponse: yakeResponseList) {
                jdbcTemplate.update("insert into article_scores_yake(article_id, ngram, score) values (?,?,?)",
                        keyHolder.getKey(),
                        yakeResponse.getNgram(),
                        yakeResponse.getScore());
            }

            for (Class currClass: classes) {
                jdbcTemplate.update("insert into classes(class_id, keyword_id, class_weight, class_name, article_id) values (?,?,?,?,?)",
                        0,
                        0,
                        currClass.getClassWeight(),
                        currClass.getClassName(),
                        keyHolder.getKey()
                );
            }

            jdbcTemplate.update(
                    "INSERT INTO" +
                            " article_params " +
                            "(article_id, language, max_ngram_size, deduplication_thresold, deduplication_algo, window_size, number_of_keywords, text) " +
                            "VALUES " +
                            "(?,?,?,?,?,?,?,?)",
                    keyHolder.getKey(),
                    articleYake.getLanguage(),
                    articleYake.getMax_ngram_size(),
                    articleYake.getDeduplication_thresold(),
                    articleYake.getDeduplication_algo(),
                    articleYake.getWindowSize(),
                    articleYake.getNumber_of_keywords(),
                    articleYake.getText()
            );
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<YakeResponse> getSavedYakeResponse(Integer yakeId) {
        return jdbcTemplate.query("select * from article_scores_yake where article_id = ?", new YakeResponseMapper(), yakeId);
    }

    @Override
    public ArticleYake getSavedAnalysedArticle(Integer yakeId) {
        return jdbcTemplate.queryForObject("select * from article_params where article_id = ?", new ArticleYakeMapper(), yakeId);
    }
}