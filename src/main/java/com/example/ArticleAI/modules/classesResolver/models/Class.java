package com.example.ArticleAI.modules.classesResolver.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Class {
    private int classId;
    private int keywordId;
    private String keywordText;
    private float classWeight;
    private String className;
}
