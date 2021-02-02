package com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality;

import com.example.ArticleAI.dto.ActualityDTO;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.classesResolver.models.Class;

import java.util.List;

public interface IActualityService {
    List<ActualityDTO> getActuality(List<Class> classes);
}
