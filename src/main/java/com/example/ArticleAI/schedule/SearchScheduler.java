package com.example.ArticleAI.schedule;


import com.example.ArticleAI.models.ClassesEmbeddings;
import com.example.ArticleAI.modules.actualityResolverService.SearchAPIService;
import com.example.ArticleAI.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchScheduler {
    private final ClassesRepository classesRepository;
    private final SearchAPIService searchAPIService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void schedulingTask() {

        log.info("Schedule update starting, retrieving search API counts");
        List<ClassesEmbeddings> searchResults = new ArrayList<>();
        try {
            searchResults = classesRepository.getAll().stream()
                    .map(clazz -> ClassesEmbeddings.builder()
                            .id(clazz.getId())
                            .embedding(clazz.getEmbedding())
                            .name(clazz.getName())
                            .actuality(searchAPIService.getSearchCount(clazz.getName())
                                    .orElseThrow(IllegalArgumentException::new))
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("Checking that API results is not null");

        if (!searchResults.isEmpty()) {
            log.info("Saving results");

            classesRepository.saveOrUpdate(searchResults);

            log.info("Classes are updated");
            return;
        }

        log.error("Search api return's empty result");
    }
}
