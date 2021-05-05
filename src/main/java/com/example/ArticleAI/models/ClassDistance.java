package com.example.ArticleAI.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassDistance {
    String keyword;
    String className;
    double distance;
    Double classActuality;
}
