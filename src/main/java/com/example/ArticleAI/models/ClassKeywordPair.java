package com.example.ArticleAI.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassKeywordPair {
    String cluster;
    String keyword;
}
