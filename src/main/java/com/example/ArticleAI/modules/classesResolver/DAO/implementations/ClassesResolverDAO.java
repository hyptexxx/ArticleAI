package com.example.ArticleAI.modules.classesResolver.DAO.implementations;

import com.example.ArticleAI.modules.classesResolver.DAO.interfaces.IClassesResolverDAO;
import com.example.ArticleAI.modules.classesResolver.mappers.ClassMapper;
import com.example.ArticleAI.modules.classesResolver.mappers.KeywordMapper;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return jdbcTemplate.query("select c.class_id,\n" +
                "       c.keyword_id,\n" +
                "       c.class_weight,\n" +
                "       class_name\n" +
                "from keywords\n" +
                "         inner join classes c on keywords.keyword_id = c.keyword_id\n" +
                "where keyword_text like ?", new ClassMapper(), keyword);
    }

    @Override
    public List<Keyword> getExistingKeywordsList() {
        return jdbcTemplate.query("select * from keywords", new KeywordMapper());
    }

    @Override
    public boolean saveNewKeyword(String keyword) {
        try {
            jdbcTemplate.update("INSERT INTO keywords(keyword_text) VALUES (?)", keyword);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }
}
