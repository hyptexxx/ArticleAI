package com.example.ArticleAI.service.interfaces.ArticleFile;

import com.example.ArticleAI.models.ArticleYake;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.FileAlreadyExistsException;

public interface IFileService {
    /**
     * @param file loaded file
     * @return true | false, if file was successfully saved/
     */
    boolean saveFileToFilesystem(MultipartFile file) throws FileAlreadyExistsException;

    /**
     * @return previously saved file
     */
    File getFile();

    ArticleYake getTextFromFile (File article, ArticleYake yake);
    ArticleYake getTextFromFile (MultipartFile article, ArticleYake yake);
}
