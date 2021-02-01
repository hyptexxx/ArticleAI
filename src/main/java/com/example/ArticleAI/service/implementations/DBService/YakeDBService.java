package com.example.ArticleAI.service.implementations.DBService;

import com.example.ArticleAI.DAO.interfaces.YakeRepository;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.service.interfaces.DBService.IYakeDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YakeDBService implements IYakeDBService {

    private final YakeRepository yakeRepository;

    @Override
    public Integer saveAnalysedArticleToDB(MultipartFile file, ArticleYake articleYake, List<YakeResponse> yakeResponseList) {
        return yakeRepository.saveAnalysedArticleToDB(articleYake, yakeResponseList);
    }

    @Override
    public List<YakeResponse> getSavedYakeResponse(Integer yakeId) {
        return yakeRepository.getSavedYakeResponse(yakeId);
    }

    @Override
    public ArticleYake getSavedAnalysedArticle(Integer yakeId) {
        return yakeRepository.getSavedAnalysedArticle(yakeId);
    }
}
