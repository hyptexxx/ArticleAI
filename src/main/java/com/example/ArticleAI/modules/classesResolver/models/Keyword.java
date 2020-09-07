package com.example.ArticleAI.modules.classesResolver.models;

public class Keyword {
    private int keywordId;
    private String keywordText;

    public Keyword(int keywordId, String keywordText) {
        this.keywordId = keywordId;
        this.keywordText = keywordText;
    }

    public Keyword() {
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

}
