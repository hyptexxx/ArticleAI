package com.example.ArticleAI.service.interfaces.RequestYake;

import com.example.ArticleAI.models.ArticleYake;

import java.io.IOException;

public interface IRequestService {
    String sendRequest(ArticleYake articleYake) throws IOException;
}
