package com.example.ArticleAI.modules.actualityResolver.DAO.implementations;

import com.example.ArticleAI.modules.actualityResolver.DAO.interfaces.IActualityResolverDAO;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActualityResolverDAO implements IActualityResolverDAO {
    private final
    JdbcTemplate jdbcTemplate;

    public ActualityResolverDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(List<Actuality> actualityPair) throws SQLIntegrityConstraintViolationException {
        List<Object[]> batch = new ArrayList<>();
        actualityPair.forEach(actuality -> batch.add(new Object[]{
                actuality.getClassId(),
                actuality.getActuality()
        }));
        try {
            jdbcTemplate.batchUpdate("insert into class_actuality(id, actuality) VALUES (?,?)", batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
