package com.example.ArticleAI.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassesRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<String> getAllClassesEmbeddings() {
        List<String> result = new ArrayList<>();
        Optional.of(jdbcTemplate.queryForList("select embedding from classes_embeddings", String.class))
                .ifPresent(list -> {
                    result.addAll(list.stream()
                            .map(String::trim)
                            .map(embedding -> embedding.replaceAll("\r\n", ""))
                            .map(embedding -> embedding.replaceAll("\\s", ""))
                            .collect(Collectors.toList()));
                });
        return result;
    }
}
