package com.example.ArticleAI.service.interfaces.ArticleFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFileService {
    boolean saveFileToFilesystem(MultipartFile file) throws IOException;
}
