package com.example.ArticleAI.modules.actualityResolver.DAO.interfaces;

import com.example.ArticleAI.modules.actualityResolver.models.Actuality;

import java.util.List;

public interface IActualityResolverDAO {
    void save (List<Actuality> actualityPair);
}
