package com.example.ArticleAI.models;

import com.example.ArticleAI.configurations.filter.AllowedContentTypes;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Builder
@Value
public class LoadedFile {
    Integer fileId;
    MultipartFile loadedFile;
    File savedFile;
    AllowedContentTypes type;
    ArticleYake articleYake;
    String articleText;
    String yakeResponse;
}
