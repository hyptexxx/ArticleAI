package com.example.ArticleAI.models;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeywordClass {
    String name;
    String embedding;
    Long classActuality;
}
