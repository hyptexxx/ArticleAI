package com.example.ArticleAI.models;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recomendation {
    String keywordClassMax;
    double actuality;
    List<ClassKeywordPair> classKeywordPairs;
    List<ClassKeywordPair> classKeywordPairMax;
    List<ClassKeywordPair> classKeywordPairMin;
    List<KeywordClass> classesActuality;
    List<NlpResponse> payload;
}
