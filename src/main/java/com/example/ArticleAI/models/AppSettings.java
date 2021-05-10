package com.example.ArticleAI.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppSettings {
    private ArticleYake yakeParams;
    private double coefficient;
}
