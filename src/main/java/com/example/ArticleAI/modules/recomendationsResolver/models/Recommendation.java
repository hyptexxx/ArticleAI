package com.example.ArticleAI.modules.recomendationsResolver.models;

public class Recommendation {

    private String recommendationText;


    public Recommendation(String recommendationText) {
        this.recommendationText = recommendationText;
    }

    public Recommendation() {

    }


    public String getRecommendationText() {
        return recommendationText;
    }

    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }
}
