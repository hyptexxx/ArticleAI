package com.example.ArticleAI.controllers;

import com.example.ArticleAI.enums.UserRoleEnum;
import com.example.ArticleAI.models.AppSettings;
import com.example.ArticleAI.parser.AppSettingsParser;
import com.example.ArticleAI.repository.AppSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppSettingsController {
    private final AppSettingsRepository appSettingsRepository;

    @GetMapping("/api/settings")
    public ResponseEntity<Object> getSettings() throws IllegalAccessException {
        final String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (hasPermission()) {
            AppSettings appSettings = appSettingsRepository.getAll();
            if (appSettings == null) {
                log.info("Не удалось для пользователя: {} найти настройки системы", userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            log.info("Пользователь: {} получил настройки системы", userId);
            return ResponseEntity.status(HttpStatus.OK).body(appSettings);

        }

        log.info("Пользователь: {} пытался получить настройки системы", userId);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @PostMapping("/api/settings")
    public ResponseEntity<Object> saveOrUpdate(@RequestParam("settings") String json) throws ParseException,
            IllegalAccessException {
        final String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Optional<AppSettings> appSettings = AppSettingsParser.parse(json);

        if (hasPermission()) {
            appSettingsRepository.save(appSettings.orElseThrow(IllegalArgumentException::new));
            log.info("Пользователь: {} обновил настройки системы: {}", userId, appSettings);

            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        log.info("Пользователь: {} пытался обновить настройки системы: {}", userId, appSettings);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    private boolean hasPermission() throws IllegalAccessException {
        final String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final String authority = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .findFirst()
                .orElseThrow(IllegalAccessException::new)
                .getAuthority();
        final UserRoleEnum userRole = Arrays.stream(UserRoleEnum.values())
                .filter(userRoleEnum -> userRoleEnum.getRoleName().equals(authority))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        if (StringUtils.isNumeric(userId)) {
            return userRole.equals(UserRoleEnum.ADMIN) || userRole.equals(UserRoleEnum.EMPLOYEE);
        }

        return false;
    }
}
