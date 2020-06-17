package com.example.ArticleAI.service.interfaces.RequestYake;

import com.example.ArticleAI.models.ArticleYake;

import java.io.IOException;

public interface IRequestService {
    /**
     * @param articleYake yake model
     * @return request result
     * @throws IOException
     */
    String sendRequest(ArticleYake articleYake) throws IOException;
}
