package com.example.ArticleAI.service;

import com.example.ArticleAI.dto.AuthResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface AuthServiceProvider<T> {
    Authentication authorizeUser(@NonNull AuthResponse<T> authResponse, @NonNull String password);

    Set<GrantedAuthority> foundRoleForUser(T utmoUser);
}
