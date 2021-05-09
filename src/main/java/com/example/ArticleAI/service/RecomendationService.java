package com.example.ArticleAI.service;

import com.example.ArticleAI.models.*;
import com.example.ArticleAI.repository.ClassesRepository;
import com.example.ArticleAI.util.MathUtil;
import com.example.ArticleAI.util.RecomendationUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecomendationService {
    private final RecomendationUtilService recomendationUtilService;

    public List<TopSubject> getClassesForPublication(List<NlpResponse> filteredKeywords,
                                                     List<ClassDistance> classDistance) {
        final List<String> keywordNames = RecomendationUtilService.getKeywordNams(filteredKeywords);
        final List<TopSubject> topSubjects = new ArrayList<>();
        final Set<String> buffer = new HashSet<>();
        List<TopSubject> result;

        keywordNames.forEach(keyword -> {
            ClassDistance minDistanceClass = classDistance.stream()
                    .filter(clazz -> clazz.getKeyword().equals(keyword))
                    .min(Comparator.comparing(ClassDistance::getDistance))
                    .orElseThrow(NoSuchElementException::new);

            topSubjects.add(TopSubject.builder()
                    .name(minDistanceClass.getClassName())
                    .percent(minDistanceClass.getDistance() * 100)
                    .build());
        });

        result = topSubjects.stream()
                .filter(topSubject -> buffer.add(topSubject.getName()))
                .collect(Collectors.toList());

        return result;
    }


    public Recommendation getRecommendation(List<TopSubject> topSubjects,
                                            List<ClassDistance> classDistance,
                                            List<NlpResponse> filteredKeywords) {

        final List<String> keywordNames = RecomendationUtilService.getKeywordNams(filteredKeywords);
        final List<ClassDistance> softMaxClasses = calculateKeywordActuality(classDistance);

        return Recommendation.builder()
                .actuality(
                        MathUtil.withBigDecimal(
                                RecomendationUtilService.getActualityByMax(softMaxClasses) * 100, 3
                        )
                )
                .hasTags(keywordNames.stream()
                        .limit(7)
                        .collect(Collectors.toList()))
                .topSubjects(topSubjects)
                .build();
    }

    public List<ClassKeywordPair> getClassesForKeywords(List<String> keywords, List<ClassDistance> classDistance) {
        final List<ClassKeywordPair> result = new ArrayList<>();

        keywords.forEach(keyword -> result.add(ClassKeywordPair.builder()
                .cluster(classDistance.stream()
                        .filter(clazz -> clazz.getKeyword().equals(keyword))
                        .min(Comparator.comparing(ClassDistance::getDistance))
                        .orElseThrow(NoSuchElementException::new)
                        .getClassName())
                .keyword(keyword)
                .build()));

        return result;
    }

    private List<ClassDistance> calculateKeywordActuality(List<ClassDistance> distance) {
        final List<ClassDistance> N = new ArrayList<>();
        final List<ClassDistance> keywords = new ArrayList<>();
        final List<KeywordClass> classes = recomendationUtilService.getKeyWordClass();
        int elemIndex;

        for (KeywordClass aClass : classes) {
            N.addAll(distance.stream()
                    .filter(classDistance -> classDistance.getClassName().equals(aClass.getName()))
                    .map(classDistance -> ClassDistance.builder()
                            .distance(classDistance.getDistance())
                            .className(classDistance.getClassName())
                            .keyword(classDistance.getKeyword())
                            .classActuality(aClass.getClassActuality())
                            .build())
                    .collect(Collectors.toList()));
        }

        for (int i = 0; i < N.size() / classes.size(); i++) {
            double sum = 0.0;
            for (int j = 0; j < classes.size(); j++) {
                elemIndex = i * (N.size() / classes.size()) + j;
                sum += N.get(elemIndex).getClassActuality() * (1 - (N.get(elemIndex)).getDistance());
            }

            N.get(i).setClassActuality(sum / classes.size());
            keywords.add(N.get(i));
        }

        return keywords;
    }
}
