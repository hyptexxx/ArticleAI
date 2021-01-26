package com.example.ArticleAI.modules.recomendationsResolver.exception;

public class RecommendationCreationException extends RuntimeException {
    public RecommendationCreationException() {
        super();
    }

    public RecommendationCreationException(String message) {
        super(message);
    }
}
