package com.example.ArticleAI.DAO.service;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IYakeDBDAO {
    boolean saveAnalysedArticleToDB(ArticleYake articleYake, List<YakeResponse> yakeResponseList);

    List<YakeResponse> getSavedYakeResponse(Integer yakeId);

    ArticleYake getSavedAnalysedArticle(Integer yakeId);
}
