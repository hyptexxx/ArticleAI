package com.example.ArticleAI.exception;

public class NlpResponseUnauthorizedException extends RuntimeException{
    public NlpResponseUnauthorizedException() {
        super();
    }

    public NlpResponseUnauthorizedException(String message) {
        super(message);
    }
}
