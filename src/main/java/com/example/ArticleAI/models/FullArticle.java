package com.example.ArticleAI.models;

import lombok.Builder;

import java.util.List;

@Builder
public class FullArticle {

    private List<YakeResponse> savedYakeResponse;
    private ArticleYake savedArticleYake;


    public FullArticle() {
    }

    public FullArticle(List<YakeResponse> savedYakeResponse, ArticleYake savedArticleYake) {
        this.savedYakeResponse = savedYakeResponse;
        this.savedArticleYake = savedArticleYake;
    }



    public List<YakeResponse> getSavedYakeResponse() {
        return savedYakeResponse;
    }

    public void setSavedYakeResponse(List<YakeResponse> savedYakeResponse) {
        this.savedYakeResponse = savedYakeResponse;
    }

    public ArticleYake getSavedArticleYake() {
        return savedArticleYake;
    }

    public void setSavedArticleYake(ArticleYake savedArticleYake) {
        this.savedArticleYake = savedArticleYake;
    }
}
