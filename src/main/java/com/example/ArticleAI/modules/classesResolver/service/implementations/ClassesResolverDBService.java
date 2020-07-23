package com.example.ArticleAI.modules.classesResolver.service.implementations;

import com.example.ArticleAI.modules.classesResolver.DAO.interfaces.IClassesResolverDAO;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import com.example.ArticleAI.modules.classesResolver.service.interfaces.IClassesResolverDBService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesResolverDBService implements IClassesResolverDBService {

    private final IClassesResolverDAO classesResolverDAO;

    public ClassesResolverDBService(IClassesResolverDAO classesResolverDAO) {
        this.classesResolverDAO = classesResolverDAO;
    }

    @Override
    public List<Class> getExistingClassList(String keyword) {
        return classesResolverDAO.getExistingClassList(keyword);
    }

    @Override
    public List<Keyword> getExistingKeywordsList() {
        return classesResolverDAO.getExistingKeywordsList();
    }

    @Override
    public boolean saveNewKeyword(String keyword) {
        return classesResolverDAO.saveNewKeyword(keyword);
    }
}
