package com.example.ArticleAI.DAO.service;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IYakeDBDAO {
    boolean saveAnalysedArticleToDB(ArticleYake articleYake, List<YakeResponse> yakeResponseList, List<Class> classes);

    List<YakeResponse> getSavedYakeResponse(Integer yakeId);

    ArticleYake getSavedAnalysedArticle(Integer yakeId);
}
