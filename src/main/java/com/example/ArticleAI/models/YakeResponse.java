package com.example.ArticleAI.models;

public class YakeResponse {
    private String ngram;
    private double score;

    public YakeResponse(String ngram, double score) {
        this.ngram = ngram;
        this.score = score;
    }

    public YakeResponse() {

    }


    public String getNgram() {
        return ngram;
    }

    public void setNgram(String ngram) {
        this.ngram = ngram;
    }

    public double getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
