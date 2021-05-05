package com.example.ArticleAI.service;

import com.example.ArticleAI.client.ClientConfigFlow;
import com.example.ArticleAI.dto.AuthResponse;
import com.example.ArticleAI.enums.UserRoleEnum;
import com.example.ArticleAI.models.UtmoUser;
import com.example.ArticleAI.util.AuthUtil;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UtMoAuthServiceProvider implements AuthServiceProvider<UtmoUser> {
    @Override
    public Authentication authorizeUser(@NonNull AuthResponse<UtmoUser> authResponse, @NonNull String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authResponse.getUser().getId(),
                AuthUtil.PASSWORD_ENCODER.encode(password),
                foundRoleForUser(authResponse.getUser())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        authResponse.setBaseClientConfig(new ClientConfigFlow<UtmoUser>().getClientConfig(authResponse.getUser()));

        return authentication;
    }

    @Override
    public Set<GrantedAuthority> foundRoleForUser(UtmoUser utmoUser) {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add((new SimpleGrantedAuthority(
                UserRoleEnum.getByUserType(utmoUser.getUser_type()).name()
        )));

        return authorities;
    }
}
