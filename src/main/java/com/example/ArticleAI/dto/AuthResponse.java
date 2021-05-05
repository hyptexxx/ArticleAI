package com.example.ArticleAI.dto;

import com.example.ArticleAI.client.BaseClientConfig;
import com.example.ArticleAI.models.ErrorsToClient;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse<T> {
    private List<ErrorsToClient> errorsToClient;
    private BaseClientConfig baseClientConfig;
    private T user;
}
