package com.example.ArticleAI.service.interfaces.ArticleFile;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface IFileService {
    /**
     * @param file loaded file
     * @return true | false, if file was successfully saved/
     */
    boolean saveFileToFilesystem(MultipartFile file);

    /**
     * @return previously saved file
     */
    File getFile();
}
