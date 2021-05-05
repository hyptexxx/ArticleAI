package com.example.ArticleAI.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtmoUserDto {
    private Integer remoteId;
    private Integer remotePin;
    private String fio;
    private Integer userType;
    private String info;
}
