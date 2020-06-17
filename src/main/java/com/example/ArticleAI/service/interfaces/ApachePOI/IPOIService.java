package com.example.ArticleAI.service.interfaces.ApachePOI;

import com.example.ArticleAI.models.ArticleYake;

import java.io.File;

public interface IPOIService {
    /**
     * @param article file
     * @param yake yake meta params
     * @return Full yake article
     */
    ArticleYake getArticleYakeText(File article, ArticleYake yake);
}
