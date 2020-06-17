package com.example.ArticleAI.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleYake {

    @Getter
    @Setter
    private String language;

    @Getter
    @Setter
    private float max_ngram_size;

    @Getter
    @Setter
    private float deduplication_thresold;

    @Getter
    @Setter
    private String deduplication_algo;

    @Getter
    @Setter
    private float windowSize;

    @Getter
    @Setter
    private float number_of_keywords;

    @Getter
    @Setter
    private String text;
}
