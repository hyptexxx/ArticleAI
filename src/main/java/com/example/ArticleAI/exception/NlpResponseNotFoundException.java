package com.example.ArticleAI.exception;

public class NlpResponseNotFoundException extends RuntimeException{
    public NlpResponseNotFoundException() {
        super();
    }

    public NlpResponseNotFoundException(String message) {
        super(message);
    }
}
