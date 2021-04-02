package com.example.ArticleAI.models;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassesEmbeddings {
    Integer id;
    String embedding;
    String name;
    Long actuality;
}
