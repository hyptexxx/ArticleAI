package com.example.ArticleAI.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualityStatsDto {
    Integer classId;
    String date;
    String className;
    Double actuality;
}
