package com.example.ArticleAI.modules.classesResolver.models;

import java.math.BigDecimal;

public class Class {
    private int classId;
    private int keywordId;
    private float classWeight;
    private String className;

    public Class(int classId, int keywordId, float classWeight, String className) {
        this.classId = classId;
        this.keywordId = keywordId;
        this.classWeight = classWeight;
        this.className = className;
    }

    public Class() {
    }


    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public float getClassWeight() {
        return classWeight;
    }

    public void setClassWeight(float classWeight) {
        this.classWeight = classWeight;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}