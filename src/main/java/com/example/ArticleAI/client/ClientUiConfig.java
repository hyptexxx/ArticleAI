package com.example.ArticleAI.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ClientUiConfig {
    public static final ClientUiConfig DEFAULT_CONFIG = ClientUiConfig.builder()
            .withCertificateGeneration(false)
            .withAnalyseInfoDisplay(false)
            .withHistoryFile(false)
            .withMonitoring(false)
            .withSettings(false)
            .build();

    private boolean withCertificateGeneration;
    private boolean withAnalyseInfoDisplay;
    private boolean withHistoryFile;
    private boolean withMonitoring;
    private boolean withSettings;
}
