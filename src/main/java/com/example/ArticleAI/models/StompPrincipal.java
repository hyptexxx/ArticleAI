package com.example.ArticleAI.models;

import java.security.Principal;

public class StompPrincipal implements Principal {
    private String name;

    public StompPrincipal(String name) {
        System.out.println(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}