package com.example.ArticleAI.modules.classesResolver.service.implementations;

import com.example.ArticleAI.modules.classesResolver.DAO.interfaces.IClassesResolverDAO;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import com.example.ArticleAI.modules.classesResolver.service.interfaces.IClassesResolverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesResolverService extends ClassesResolverDBService implements IClassesResolverService {

    public ClassesResolverService(IClassesResolverDAO classesResolverDAO) {
        super(classesResolverDAO);
    }

    @Override
    public boolean isKeywordExistsInExistingClassesList(String keyword, List<Keyword> existingKeywordsList) {
        boolean result = false;
        for (Keyword existingKeyword : existingKeywordsList) {
            if (existingKeyword.getKeywordText().equals(keyword)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
