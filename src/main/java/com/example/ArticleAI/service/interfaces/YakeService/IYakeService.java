package com.example.ArticleAI.service.interfaces.YakeService;

import com.example.ArticleAI.models.YakeResponse;

import java.util.List;

public interface IYakeService {
    List<YakeResponse> parseYakeResponseJSON(String yakeResponseJSON);
}
