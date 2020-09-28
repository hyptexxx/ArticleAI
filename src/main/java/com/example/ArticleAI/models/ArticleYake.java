package com.example.ArticleAI.models;


public class ArticleYake {

    private float max_ngram_size;
    private float deduplication_thresold;
    private float windowSize;
    private int number_of_keywords;
    private String deduplication_algo;
    private String language;
    private String text;


    public ArticleYake(String language, float max_ngram_size, float deduplication_thresold, String deduplication_algo, float windowSize, int number_of_keywords, String text) {
        this.language = language;
        this.max_ngram_size = max_ngram_size;
        this.deduplication_thresold = deduplication_thresold;
        this.deduplication_algo = deduplication_algo;
        this.windowSize = windowSize;
        this.number_of_keywords = number_of_keywords;
        this.text = text;
    }

    public ArticleYake() {

    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public float getMax_ngram_size() {
        return max_ngram_size;
    }

    public void setMax_ngram_size(float max_ngram_size) {
        this.max_ngram_size = max_ngram_size;
    }

    public float getDeduplication_thresold() {
        return deduplication_thresold;
    }

    public void setDeduplication_thresold(float deduplication_thresold) {
        this.deduplication_thresold = deduplication_thresold;
    }

    public String getDeduplication_algo() {
        return deduplication_algo;
    }

    public void setDeduplication_algo(String deduplication_algo) {
        this.deduplication_algo = deduplication_algo;
    }

    public float getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(float windowSize) {
        this.windowSize = windowSize;
    }

    public int getNumber_of_keywords() {
        return number_of_keywords;
    }

    public void setNumber_of_keywords(int number_of_keywords) {
        this.number_of_keywords = number_of_keywords;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
