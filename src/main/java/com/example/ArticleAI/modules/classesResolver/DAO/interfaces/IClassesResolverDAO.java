package com.example.ArticleAI.modules.classesResolver.DAO.interfaces;

import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;

import java.util.List;

public interface IClassesResolverDAO {
    List<Class> getExistingClassList(String keyword);
    List<Keyword> getExistingKeywordsList();
    boolean saveNewKeyword(String keyword);
}
