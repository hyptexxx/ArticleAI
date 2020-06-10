package com.example.ArticleAI.controllers.service.interfaces.ArticleFile;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    boolean saveFileToFilesystem(MultipartFile file);
}
