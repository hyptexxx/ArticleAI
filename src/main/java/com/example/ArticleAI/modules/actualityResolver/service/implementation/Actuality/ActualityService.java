package com.example.ArticleAI.modules.actualityResolver.service.implementation.Actuality;

import com.example.ArticleAI.modules.actualityResolver.DAO.interfaces.IActualityResolverDAO;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.actualityResolver.parser.ClassParser;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality.IActualityService;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.SearchAPIService.ISearchAPIService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActualityService implements IActualityService {

    private final
    ISearchAPIService searchAPIService;

    private final
    IActualityResolverDAO actualityResolverDAO;

    public ActualityService(ISearchAPIService searchAPIService, IActualityResolverDAO actualityResolverDAO) {
        this.searchAPIService = searchAPIService;
        this.actualityResolverDAO = actualityResolverDAO;
    }

    @Override
    public List<Actuality> getActuality(String classes) {
        List<Actuality> actualityPair = new ArrayList<>();
        new ClassParser().getClassesFromJSON(classes).forEach(parsedClass ->
                actualityPair.add(new Actuality(parsedClass.getClassId(), searchAPIService.getSearchCount(parsedClass)))
        );
        actualityResolverDAO.save(actualityPair);
        return actualityPair;
    }
}
