package com.example.ArticleAI.models;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recommendation {
    double actuality;
    List<TopSubject> topSubjects;
    List<String> hasTags;
    List<NlpResponse> payload;
    List<KeywordClass> classesActuality;
    List<YakeResponse> yakeResponse;
    List<ClassDistance> keywordActuality;
}
