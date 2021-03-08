package com.example.ArticleAI.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NlpResponse {
    String ngram;
    double value;
    int isGood;
}
