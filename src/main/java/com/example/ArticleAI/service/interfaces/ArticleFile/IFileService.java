package com.example.ArticleAI.service.interfaces.ArticleFile;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    boolean saveFileToFilesystem(MultipartFile file);
}
