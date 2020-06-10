package com.example.ArticleAI.controllers.service.implementations.ArticleFile;

import com.example.ArticleAI.controllers.service.interfaces.ArticleFile.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService implements IFileService {

    @Override
    public boolean saveFileToFilesystem(MultipartFile file) {
        return false;
    }


}
