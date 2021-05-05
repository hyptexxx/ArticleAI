package com.example.ArticleAI.controllers;

import com.example.ArticleAI.client.BaseClientConfig;
import com.example.ArticleAI.client.ClientConfigFlow;
import com.example.ArticleAI.dto.AuthResponse;
import com.example.ArticleAI.enums.UserRoleEnum;
import com.example.ArticleAI.models.UtmoUser;
import com.example.ArticleAI.repository.UserRepository;
import com.example.ArticleAI.service.AuthService;
import com.example.ArticleAI.service.AuthServiceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthEndpoint {

    private final AuthServiceProvider<UtmoUser> authServiceProvider;
    private final ClientConfigFlow<UtmoUser> ClientConfigFlow;
    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/api/auth")
    public ResponseEntity<AuthResponse<UtmoUser>> doAuth(@RequestParam("login") String login,
                                                         @RequestParam("password") String password) throws IOException {

        final AuthResponse<UtmoUser> authResponse = authService.startParseJSON(
                authService.sendAuthRequest(login, password)
        );

        if (authResponse.getUser() == null && authResponse.getErrorsToClient().size() > 0) {
            log.info("Authentication failed for for user: {}", login);
            authResponse.getErrorsToClient().forEach(errorsToClient -> {
                log.info("trace errors: {} cause: {}, message: {}",
                        errorsToClient.getErrorName(), errorsToClient.getCause(), errorsToClient.getMessage());
            });

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(authResponse);
        }

        if (userRepository.isUserRegistered(authResponse.getUser())) {
            authServiceProvider.authorizeUser(authResponse, password);

            log.info("User: {} are authenticated with authorities: {}.",
                    authResponse.getUser().getFio(),
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponse);
        }

        if (userRepository.save(authResponse.getUser())) {
            authServiceProvider.authorizeUser(authResponse, password);

            log.info("User: {} are registered and authenticated with authorities: {}.",
                    authResponse.getUser().getFio(),
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponse);
        }

        log.info("Auth for {} failed.", authResponse.getUser().getFio());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(authResponse);
    }

    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName() + "\t" + "Вышел из аккаунта");

        SecurityContextLogoutHandler securityContextHolder = new SecurityContextLogoutHandler();
        securityContextHolder.logout(request, response, null);
    }

    @PostMapping("/api/heartbeat")
    public ResponseEntity<Object> heartbeat() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @PostMapping("/api/user/config")
    public ResponseEntity<BaseClientConfig> getUserConfig() throws IllegalAccessException {
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

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ClientConfigFlow.getByRole(userRole));
    }
}
