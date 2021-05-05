package com.example.ArticleAI.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtmoUser {
    private int id;
    private int pin;
    private String info;
    private boolean success;
    private String fio;
    private int admin_auth;
    private int editor_auth;
    private int user_type;
    private int first_auth;
    private String email;
    private int allowed;
    private String avatar;
}
