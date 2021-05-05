package com.example.ArticleAI.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class AuthUtil {
    public final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
}
