package com.example.ArticleAI.service.implementations.DBService;

import com.example.ArticleAI.DAO.service.IYakeDBDAO;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.service.interfaces.DBService.IYakeDBService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class YakeDBService implements IYakeDBService {

    private final
    IYakeDBDAO iYakeDBDAO;

    public YakeDBService(IYakeDBDAO iYakeDBDAO) {
        this.iYakeDBDAO = iYakeDBDAO;
    }

    @Override
    public boolean saveAnalysedArticleToDB(MultipartFile file, ArticleYake articleYake, List<YakeResponse> yakeResponseList) {
        return iYakeDBDAO.saveAnalysedArticleToDB(articleYake, yakeResponseList);
    }
}
