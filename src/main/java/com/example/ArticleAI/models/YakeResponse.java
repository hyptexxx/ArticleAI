package com.example.ArticleAI.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YakeResponse {
    private String ngram;
    private double score;
}
