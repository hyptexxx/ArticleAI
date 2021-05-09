package com.example.ArticleAI.repository;

import com.example.ArticleAI.dto.ActualityStatsDto;
import com.example.ArticleAI.mappers.ActualityStatsDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActualityRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<ActualityStatsDto> getClassActualityByClassId(Integer classId) {
        return jdbcTemplate.query("select class_actuality.class_id,\n" +
                "       class_actuality.date,\n" +
                "       ce.name,\n" +
                "       class_actuality.actuality\n" +
                "from class_actuality\n" +
                "         inner join classes ce\n" +
                "                    on class_actuality.class_id = ce.id\n" +
                "where class_actuality.class_id = ?\n" +
                "group by class_actuality.date,\n" +
                "         class_actuality.class_id,\n" +
                "         ce.name,\n" +
                "         class_actuality.actuality", new ActualityStatsDtoMapper(), classId);
    }
}
