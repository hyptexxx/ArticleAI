package com.example.ArticleAI.modules.classesResolver.service;

import com.example.ArticleAI.modules.classesResolver.DAO.interfaces.IClassesResolverDAO;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesResolverService extends ClassesResolverDBService {

    public ClassesResolverService(IClassesResolverDAO classesResolverDAO) {
        super(classesResolverDAO);
    }

    public boolean isKeywordExistsInExistingClassesList(String keyword, List<Keyword> existingKeywordsList) {
        return existingKeywordsList.stream()

                .anyMatch(keywordInSearch -> keywordInSearch.getKeywordText().equals(keyword));
    }
}
