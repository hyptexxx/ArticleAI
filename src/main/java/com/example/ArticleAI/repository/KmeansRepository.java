package com.example.ArticleAI.repository;

import com.example.ArticleAI.mappers.YakeResponseMapper;
import com.example.ArticleAI.models.YakeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KmeansRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<List<YakeResponse>> getAllKeywords() {
        return Optional.of(jdbcTemplate.query("select ngram, score from article_scores_yake", new YakeResponseMapper()));
    }
}
