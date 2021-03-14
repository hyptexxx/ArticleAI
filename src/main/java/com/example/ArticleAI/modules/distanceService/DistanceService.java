package com.example.ArticleAI.modules.distanceService;

import com.example.ArticleAI.mappers.ClassDistanceMapper;
import com.example.ArticleAI.models.ClassDistance;
import com.example.ArticleAI.models.ClassKeywordPair;
import com.example.ArticleAI.models.KeywordClass;
import com.example.ArticleAI.models.Recomendation;
import com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests.SearchAPIService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DistanceService {
    private final SearchAPIService searchAPIService;

    @SneakyThrows
    public Recomendation getDistance(List<String> keywords,
                                     List<KeywordClass> classesEmbeddings) {
        Map<String, Object> req_payload = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        req_payload.put("classesEmbeddings", classesEmbeddings);
        req_payload.put("keywords", keywords);

        HttpEntity<?> entity = new HttpEntity(req_payload, headers);

        ResponseEntity<String> response = new RestTemplate()
                .postForEntity("http://10.10.1.28:8081/api/v1/class/range", entity, String.class);

        List<ClassDistance> classDistance = ClassDistanceMapper.parse(response.getBody()).get();

        List<ClassKeywordPair> classKeywordPairs
                = getClassKeywordPair(getClassesForKeywords(keywords, classDistance), classesEmbeddings);

        return Recomendation.builder()
                .actuality(getActualityPercent(getMaxActualityClass(classesEmbeddings),
                        getClassesForKeywords(keywords, classDistance),
                        classesEmbeddings))
                .keywordClassMax(getMaxActualityClass(classesEmbeddings).getName())
                .classKeywordPairs(classKeywordPairs)
                .classKeywordPairMin(classKeywordPairs.stream()
                        .min(Comparator.comparing(ClassKeywordPair::getActuality))
                        .orElseThrow(NoSuchElementException::new))
                .classKeywordPairMax(classKeywordPairs.stream()
                        .max(Comparator.comparing(ClassKeywordPair::getActuality))
                        .orElseThrow(NoSuchElementException::new))
                .classesActuality(classesEmbeddings.stream()
                        .sorted(Comparator.comparing(KeywordClass::getClassActuality))
                        .collect(Collectors.toList()))
                .build();
    }

    public List<ClassKeywordPair> getClassesForKeywords(List<String> keywords, List<ClassDistance> classDistance) {
        List<ClassKeywordPair> result = new ArrayList<>();
        keywords.forEach(keyword -> result.add(ClassKeywordPair.builder()
                .cluster(classDistance.stream()
                        .filter(clazz -> clazz.getKeyword().equals(keyword))
                        .min(Comparator.comparing(ClassDistance::getDistance))
                        .orElseThrow(NoSuchElementException::new).getClassName())
                .keyword(keyword)
                .build()));
        return result;
    }

    public List<ClassKeywordPair> getClassKeywordPair(List<ClassKeywordPair> classKeywords,
                                                      List<KeywordClass> classesEmbeddings) {
        List<ClassKeywordPair> result = new ArrayList<>();
        classKeywords.forEach(className -> result.add(ClassKeywordPair.builder()
                .keyword(className.getKeyword())
                .cluster(classesEmbeddings.stream()
                        .filter(clazz -> clazz.getName().equals(className.getCluster()))
                        .max(Comparator.comparing(KeywordClass::getClassActuality))
                        .orElseThrow(NoSuchElementException::new).getName())
                .actuality(classesEmbeddings.stream()
                        .filter(e -> e.getName().equals(className.getCluster()))
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new)
                        .getClassActuality())
                .build()));
        return result;
    }

    public KeywordClass getMaxActualityClass(List<KeywordClass> classesEmbeddings) {
        return classesEmbeddings.stream()
                .max(Comparator.comparing(KeywordClass::getClassActuality))
                .orElseThrow(NoSuchElementException::new);
    }

    public double getActualityPercent(KeywordClass maxActuality,
                                      List<ClassKeywordPair> classKeywords,
                                      List<KeywordClass> classesEmbeddings) {
        List<Long> actualities = new ArrayList<>();
        classKeywords.forEach(className -> actualities.addAll(classesEmbeddings.stream()
                .filter(clazz -> clazz.getName().equals(className.getCluster()))
                .map(KeywordClass::getClassActuality)
                .collect(Collectors.toList())));

        return withBigDecimal(actualities.stream()
                .map(row -> (row * 100) / maxActuality.getClassActuality())
                .mapToDouble(Long::doubleValue)
                .average()
                .orElseThrow(NoSuchElementException::new), 3);
    }

    public static double withBigDecimal(double value, int places) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}