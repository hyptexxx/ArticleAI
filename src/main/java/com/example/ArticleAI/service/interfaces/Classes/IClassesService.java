package com.example.ArticleAI.service.interfaces.Classes;

import com.example.ArticleAI.modules.classesResolver.models.Class;

import java.util.List;

public interface IClassesService {
    List<Class> parseClasses(String ClassesJSON);
}
