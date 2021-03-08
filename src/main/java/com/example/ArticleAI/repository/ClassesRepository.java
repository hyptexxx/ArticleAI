package com.example.ArticleAI.repository;

import com.example.ArticleAI.mappers.ClassEmbeddingMapper;
import com.example.ArticleAI.models.KeywordClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassesRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<KeywordClass> getAllClassesEmbeddings() {
        List<KeywordClass> result;
        result = jdbcTemplate.query("select embedding, class_name from classes_embeddings", new ClassEmbeddingMapper());
        return result;
    }
}
