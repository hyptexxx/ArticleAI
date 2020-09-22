package com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality;

import com.example.ArticleAI.modules.actualityResolver.models.Actuality;

import java.util.List;

public interface IActualityService {
    List<Actuality> getActuality(String classes);
}
