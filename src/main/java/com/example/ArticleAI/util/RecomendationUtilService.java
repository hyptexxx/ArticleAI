package com.example.ArticleAI.util;

import com.example.ArticleAI.models.ClassDistance;
import com.example.ArticleAI.models.KeywordClass;
import com.example.ArticleAI.models.NlpResponse;
import com.example.ArticleAI.repository.ClassesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecomendationUtilService {
    private final ClassesRepository classesRepository;

    public static List<String> getKeywordNams (List<NlpResponse> filteredKeywords) {
        return filteredKeywords.stream()
                .map(NlpResponse::getNgram)
                .collect(Collectors.toList());
    }

    public List<KeywordClass> getKeyWordClass() {
        List<KeywordClass> classes = classesRepository.getAllClassesEmbeddings();

        return classes.stream()
                .map(clazz -> KeywordClass.builder()
                        .classActuality(classesRepository.getActualityByClassName(clazz.getName())
                                .orElseThrow(IllegalAccessError::new))
                        .name(clazz.getName())
                        .embedding(clazz.getEmbedding())
                        .build())
                .collect(Collectors.toList());
    }

    public static double getMaxActuality(List<ClassDistance> classDistances) {
        return classDistances.stream()
                .max(Comparator.comparing(ClassDistance::getClassActuality))
                .map(ClassDistance::getClassActuality)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static double getActuality(List<ClassDistance> classDistances) {
        return classDistances.stream()
                .map(ClassDistance::getClassActuality)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
