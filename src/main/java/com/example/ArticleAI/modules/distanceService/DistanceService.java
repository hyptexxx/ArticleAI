package com.example.ArticleAI.modules.distanceService;

import com.example.ArticleAI.models.ClassDistance;
import com.example.ArticleAI.models.KeywordClass;
import com.example.ArticleAI.parser.ClassDistanceParser;
import com.example.ArticleAI.util.MathUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DistanceService {

    @SneakyThrows
    public List<ClassDistance> getDistance(List<String> keywords, List<KeywordClass> classesEmbeddings) {
        Map<String, Object> req_payload = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        req_payload.put("classesEmbeddings", classesEmbeddings);
        req_payload.put("keywords", keywords);

        HttpEntity<?> entity = new HttpEntity(req_payload, headers);

        ResponseEntity<String> response = new RestTemplate()
                .postForEntity("http://10.10.1.28:8081/api/v1/class/range", entity, String.class);

        List<ClassDistance> classDistance = ClassDistanceParser.parse(response.getBody())
                .orElseThrow(IllegalArgumentException::new);

        normalizeAll(classDistance);

        return classDistance;
    }

    @Deprecated
    private static void normalizeAll(List<ClassDistance> searchResults) {
        log.info("Normalizing results");
        double max = searchResults.stream()
                .max(Comparator.comparing(ClassDistance::getDistance))
                .orElseThrow(IllegalArgumentException::new)
                .getDistance();

        for (ClassDistance searchResult : searchResults) {
            searchResult.setDistance(searchResult.getDistance() / max);
        }
    }
}