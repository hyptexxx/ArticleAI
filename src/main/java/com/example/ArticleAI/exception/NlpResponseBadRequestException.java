package com.example.ArticleAI.exception;

public class NlpResponseBadRequestException extends RuntimeException{
    public NlpResponseBadRequestException() {
        super();
    }

    public NlpResponseBadRequestException(String message) {
        super(message);
    }
}
