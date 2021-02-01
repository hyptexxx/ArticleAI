package com.example.ArticleAI.dto;

import com.example.ArticleAI.models.YakeResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YakeDTO {
    List<YakeResponse> yakeResponse;
    Integer generatedArticleId;
}
