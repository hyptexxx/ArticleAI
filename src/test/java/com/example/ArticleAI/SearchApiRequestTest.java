package com.example.ArticleAI;

import com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests.SearchAPIService;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchApiRequestTest {
    @Test
    void testNlpRequest() {
        List<Class> classes = new ArrayList<>();
        Map<Class, Long> actualityPairs = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            classes.add(Class.builder()
                    .className("test class name " + i)
                    .build());

        }

        classes.forEach(clazz -> actualityPairs.put(clazz, new SearchAPIService().getSearchCount("clazz")));
        Assertions.assertNotNull(actualityPairs);
    }
}
