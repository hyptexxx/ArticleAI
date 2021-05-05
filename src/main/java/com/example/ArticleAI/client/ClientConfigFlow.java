package com.example.ArticleAI.client;

import com.example.ArticleAI.enums.UserRoleEnum;
import com.example.ArticleAI.models.UtmoUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.example.ArticleAI.client.ClientUiConfig.DEFAULT_CONFIG;

@Service
public class ClientConfigFlow<T> {
    private static final Map<UserRoleEnum, BaseClientConfig> CLIENT_CONFIGS = new HashMap<>();

    public ClientConfigFlow() {
        CLIENT_CONFIGS.put(UserRoleEnum.ADMIN, BaseClientConfig.builder()
                .clientUiConfig(ClientUiConfig.builder()
                        .withCertificateGeneration(true)
                        .withAnalyseInfoDisplay(true)
                        .withHistoryFile(true)
                        .withMonitoring(true)
                        .withSettings(true)
                        .build()
                ).build()
        );

        CLIENT_CONFIGS.put(UserRoleEnum.EMPLOYEE, BaseClientConfig.builder()
                .clientUiConfig(ClientUiConfig.builder()
                        .withCertificateGeneration(true)
                        .withAnalyseInfoDisplay(true)
                        .withHistoryFile(true)
                        .withMonitoring(true)
                        .withSettings(true)
                        .build()
                ).build()
        );

        CLIENT_CONFIGS.put(UserRoleEnum.STUDENT, BaseClientConfig.builder()
                .clientUiConfig(ClientUiConfig.builder()
                        .withCertificateGeneration(true)
                        .withHistoryFile(true)
                        .build()
                ).build()
        );

        CLIENT_CONFIGS.put(UserRoleEnum.ANONYMOUS, BaseClientConfig.builder()
                .clientUiConfig(DEFAULT_CONFIG)
                .build()
        );
    }

    public BaseClientConfig getClientConfig(T user) {
        if (user instanceof UtmoUser) {
            return CLIENT_CONFIGS.get(UserRoleEnum.getByUserType(((UtmoUser) user).getUser_type()));
        }

        return CLIENT_CONFIGS.get(UserRoleEnum.ANONYMOUS);
    }

    public BaseClientConfig getByRole(UserRoleEnum role) {
        final BaseClientConfig baseClientConfig = CLIENT_CONFIGS.get(role);
        if (baseClientConfig != null) {
            return baseClientConfig;
        }

        return CLIENT_CONFIGS.get(UserRoleEnum.ANONYMOUS);
    }
}
