package com.example.ArticleAI.service.interfaces.DBService;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IYakeDBService {
    Integer saveAnalysedArticleToDB(MultipartFile file, ArticleYake articleYake, List<YakeResponse> yakeResponseList);

    List<YakeResponse> getSavedYakeResponse(Integer yakeId);

    ArticleYake getSavedAnalysedArticle(Integer yakeId);
}
