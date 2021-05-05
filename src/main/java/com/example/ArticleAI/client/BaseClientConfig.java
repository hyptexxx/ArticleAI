package com.example.ArticleAI.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseClientConfig {
    private ClientUiConfig clientUiConfig;
}

//todo security url and files
//todo recommendation algo
//todo algo
//todo actuality to pdf