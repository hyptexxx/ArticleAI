package com.example.ArticleAI.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorsToClient {
    private String errorName;
    private String message;
    private String cause;
}
