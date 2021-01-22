package com.example.ArticleAI.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleYake {
    float max_ngram_size;
    float deduplication_thresold;
    float windowSize;
    int number_of_keywords;
    String deduplication_algo;
    String language;
    String text;
}
