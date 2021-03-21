package com.example.ArticleAI.configurations.filter;

import com.example.ArticleAI.models.LoadedFile;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllowedFile {
        LoadedFile loadedFile;
        Boolean isAllowed;
}
