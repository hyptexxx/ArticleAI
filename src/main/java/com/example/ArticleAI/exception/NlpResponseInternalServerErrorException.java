package com.example.ArticleAI.exception;

public class NlpResponseInternalServerErrorException extends RuntimeException{
    public NlpResponseInternalServerErrorException() {
        super();
    }

    public NlpResponseInternalServerErrorException(String message) {
        super(message);
    }
}
