package com.example.ArticleAI.modules.classesResolver.service.interfaces;

import com.example.ArticleAI.modules.classesResolver.models.Keyword;

import java.util.List;

public interface IClassesResolverService extends IClassesResolverDBService {
    boolean isKeywordExistsInExistingClassesList(String keyword, List<Keyword> existingClassList);
}
