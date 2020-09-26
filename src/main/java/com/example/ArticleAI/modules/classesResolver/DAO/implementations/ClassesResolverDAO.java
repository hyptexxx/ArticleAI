package com.example.ArticleAI.modules.classesResolver.DAO.implementations;

import com.example.ArticleAI.modules.classesResolver.DAO.interfaces.IClassesResolverDAO;
import com.example.ArticleAI.modules.classesResolver.mappers.ClassMapper;
import com.example.ArticleAI.modules.classesResolver.mappers.KeywordMapper;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ClassesResolverDAO implements IClassesResolverDAO {

    private final
    JdbcTemplate jdbcTemplate;

    public ClassesResolverDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Class> getExistingClassList(String keyword) {
        return jdbcTemplate.query("select c.id,\n" +
                "       c.keyword_id,\n" +
                "       keyword_text,\n" +
                "       c.class_weight,\n" +
                "       class_name\n" +
                "from keywords\n" +
                "         inner join classes c on keywords.id = c.keyword_id\n" +
                "where keyword_text like ?", new ClassMapper(), keyword);
    }

    @Override
    public List<Keyword> getExistingKeywordsList() {
        return jdbcTemplate.query("select * from keywords", new KeywordMapper());
    }

    @Override
    public boolean saveNewKeyword(String keyword, Integer articleId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection
                        .prepareStatement(
                                "INSERT INTO keywords(keyword_text, article_id) VALUES (?,?)",
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
                ps.setString(1, keyword);
                ps.setInt(2, articleId);
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            return false;
        }
        for (String existingClass : getAllExistingClasses()) {
            jdbcTemplate.update("insert into classes(keyword_id, class_weight, class_name) values (?,?,?)",
                    keyHolder.getKey(), 0, existingClass);
        }
        return true;
    }

    private List<String> getAllExistingClasses() {
        return jdbcTemplate.queryForList("select class_name from classes group by class_name", String.class);
    }
}
