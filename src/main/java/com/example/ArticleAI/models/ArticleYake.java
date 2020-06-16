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
    private int max_ngram_size;

    @Getter
    @Setter
    private int number_of_keywords;

    @Getter
    @Setter
    private String text;
}
