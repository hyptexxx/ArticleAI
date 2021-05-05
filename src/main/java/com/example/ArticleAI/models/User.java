package com.example.ArticleAI.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class User {
    String login;
    String password;
}
