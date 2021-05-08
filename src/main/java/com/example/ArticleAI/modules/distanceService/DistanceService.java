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

        ////
//        calculateKeywordActuality(classesEmbeddings, classDistance);
//        List<ClassKeywordPair> classKeywordPairs
//                = getClassKeywordPair(getClassesForKeywords(keywords, classDistance), classesEmbeddings);
//
//        return Recomendation.builder()
//                .actuality(getActualityPercent(getMaxActualityClass(classesEmbeddings),
//                        getClassesForKeywords(keywords, classDistance),
//                        classesEmbeddings))
//                .keywordClassMax(getMaxActualityClass(classesEmbeddings).getName())
//                .classKeywordPairs(classKeywordPairs)
//                .classKeywordPairMin(classKeywordPairs.stream()
//                        .sorted(Comparator.comparing(ClassKeywordPair::getActuality))
//                        .limit(7)
//                        .collect(Collectors.toList()))
//                .classKeywordPairMax(classKeywordPairs.stream()
//                        .sorted(Comparator.comparing(ClassKeywordPair::getActuality)
//                                .reversed())
//                        .limit(7)
//                        .collect(Collectors.toList()))
//                .classesActuality(classesEmbeddings.stream()
//                        .sorted(Comparator.comparing(KeywordClass::getClassActuality))
//                        .collect(Collectors.toList()))
//                .payload(payload)
//                .build();
//    }
//

//
//    public List<ClassKeywordPair> getClassKeywordPair(List<ClassKeywordPair> classKeywords,
//                                                      List<KeywordClass> classesEmbeddings) {
//        List<ClassKeywordPair> result = new ArrayList<>();
//        classKeywords.forEach(className -> result.add(ClassKeywordPair.builder()
//                .keyword(className.getKeyword())
//                .cluster(classesEmbeddings.stream()
//                        .filter(clazz -> clazz.getName().equals(className.getCluster()))
//                        .max(Comparator.comparing(KeywordClass::getClassActuality))
//                        .orElseThrow(NoSuchElementException::new).getName())
//                .actuality(classesEmbeddings.stream()
//                        .filter(e -> e.getName().equals(className.getCluster()))
//                        .findFirst()
//                        .orElseThrow(NoSuchElementException::new)
//                        .getClassActuality())
//                .build()));
//        return result;
//    }
//
//    public KeywordClass getMaxActualityClass(List<KeywordClass> classesEmbeddings) {
//        return classesEmbeddings.stream()
//                .max(Comparator.comparing(KeywordClass::getClassActuality))
//                .orElseThrow(NoSuchElementException::new);
//    }
//
//    public double getActualityPercent(KeywordClass maxActuality,
//                                      List<ClassKeywordPair> classKeywords,
//                                      List<KeywordClass> classesEmbeddings) {
//        List<Double> actualities = new ArrayList<>();
//        classKeywords.forEach(className -> actualities.addAll(classesEmbeddings.stream()
//                .filter(clazz -> clazz.getName().equals(className.getCluster()))
//                .map(KeywordClass::getClassActuality)
//                .collect(Collectors.toList())));
//
//        return withBigDecimal(actualities.stream()
//                .map(row -> (row * 100) / maxActuality.getClassActuality())
//                .mapToDouble(Double::doubleValue)
//                .average()
//                .orElseThrow(NoSuchElementException::new), 3);
//    }
//

//
    private static void normalizeAll(List<ClassDistance> searchResults) {
        log.info("Normalizing results");
        double[] normilized = MathUtil.stableSoftmax(searchResults.stream()
                .mapToDouble(ClassDistance::getDistance)
                .toArray());

        for (int i = 0; i < searchResults.size(); i++) {
            searchResults.get(i).setDistance(normilized[i]);
        }
    }

//    private void calculateKeywordActuality(List<KeywordClass> classes, List<ClassDistance> distance) {
//
//        List<ClassDistance> N = new ArrayList<>();
//        for (KeywordClass aClass : classes) {
//            N.addAll(distance.stream()
//                    .filter(classDistance -> classDistance.getClassName().equals(aClass.getName()))
//                    .map(classDistance -> ClassDistance.builder()
//                            .distance(classDistance.getDistance())
//                            .className(classDistance.getClassName())
//                            .keyword(classDistance.getKeyword())
//                            .classActuality(aClass.getClassActuality())
//                            .build())
//                    .collect(Collectors.toList()));
//        }
//
//        final List<ClassDistance> keywords = new ArrayList<>();
//        int elemIndex;
//
//        for (int i = 0; i < N.size() / classes.size(); i++) {
//            double sum = 0.0;
//            for (int j = 0; j < classes.size(); j++) {
//                elemIndex = N.size() / classes.size() + j;
//                sum += N.get(elemIndex).getClassActuality() * (1 - (N.get(elemIndex)).getDistance());
//            }
//
//            N.get(i).setClassActuality(sum / classes.size());
//            keywords.add(N.get(i));
//        }
//    }
}