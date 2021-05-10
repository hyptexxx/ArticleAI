package com.example.ArticleAI.repository;

import com.example.ArticleAI.mappers.AppSettingsMapper;
import com.example.ArticleAI.models.AppSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppSettingsRepository {
    private final JdbcTemplate jdbcTemplate;

    public AppSettings getAll() {
        AppSettings appSettings;
        try {
            appSettings = jdbcTemplate.queryForObject("select * from app_settings", new AppSettingsMapper());
            return appSettings;
        } catch (Exception e) {
            log.error("Не возможно получить настройки системы");
        }

        return null;
    }

    public void save(AppSettings appSettings) {
        jdbcTemplate.update("delete from app_settings");
        jdbcTemplate.update("insert into app_settings(coefficient, " +
                "language," +
                "max_ngram_size, " +
                "deduplication_thresold," +
                " deduplication_algo," +
                " window_size," +
                " number_of_keywords) values (?,?,?,?,?,?,?)", appSettings.getCoefficient(),
                appSettings.getYakeParams().getLanguage(),
                appSettings.getYakeParams().getMax_ngram_size(),
                appSettings.getYakeParams().getDeduplication_thresold(),
                appSettings.getYakeParams().getDeduplication_algo(),
                appSettings.getYakeParams().getWindowSize(),
                appSettings.getYakeParams().getNumber_of_keywords() );
    }
}