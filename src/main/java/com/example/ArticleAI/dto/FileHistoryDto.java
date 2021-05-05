package com.example.ArticleAI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileHistoryDto {
    Integer publicationId;
    Integer certificateId;
    String fio;
    String publicationName;
    String certificateName;
    String publicationUploadDate;
    String certificateGenerationDate;
    double actuality;
}
