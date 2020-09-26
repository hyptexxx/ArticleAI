package com.example.ArticleAI.modules.actualityResolver.models;

public class Actuality {

    private int classId;
    private Integer actuality;


    public Actuality(int classId, Integer actuality) {
        this.classId = classId;
        this.actuality = actuality;
    }

    public Actuality() {
    }


    public Integer getActuality() {
        return actuality;
    }

    public void setActuality(Integer actuality) {
        this.actuality = actuality;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
