package com.example.ArticleAI.modules.classesResolver.service;

import com.example.ArticleAI.modules.classesResolver.DAO.interfaces.IClassesResolverDAO;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesResolverDBService {

    private final IClassesResolverDAO classesResolverDAO;

    public ClassesResolverDBService(IClassesResolverDAO classesResolverDAO) {
        this.classesResolverDAO = classesResolverDAO;
    }

    public List<Class> getExistingClassList(String keyword) {
        return classesResolverDAO.getExistingClassList(keyword);
    }

    public List<Keyword> getExistingKeywordsList() {
        return classesResolverDAO.getExistingKeywordsList();
    }

    public boolean saveNewKeyword(String keyword, Integer articleId) {
        return classesResolverDAO.saveNewKeyword(keyword, articleId);
    }
}
