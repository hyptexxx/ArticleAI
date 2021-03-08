package com.example.ArticleAI.modules.distanceService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistanceService {
    public String getDistance(String keywordEmbedding, List<String> classesEmbeddings){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        Map<String, Object> params = new HashMap<>();
        params.put("classesEmbeddings", classesEmbeddings);
        params.put("keywordEmbedding", keywordEmbedding);


        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8081/api/v1/class/range")
                .queryParam("classesEmbeddings", classesEmbeddings)
                .queryParam("keywordEmbedding", keywordEmbedding);


        HttpEntity entity = new HttpEntity(headers);

        System.out.println(builder.toUriString());
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class,
                params);
        return null;
    }
}
