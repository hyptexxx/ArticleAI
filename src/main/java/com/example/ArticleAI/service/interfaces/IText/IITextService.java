package com.example.ArticleAI.service.interfaces.IText;

import com.example.ArticleAI.models.ArticleYake;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface IITextService {

    /**
     * @param article file
     * @param yake yake meta params
     * @return Full yake article
     */
    ArticleYake getYakeTextFromPDF(MultipartFile article, ArticleYake yake) throws IOException;
}
