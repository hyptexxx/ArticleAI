package com.example.ArticleAI.modules.actualityResolver.service.implementation.Actuality;

import com.example.ArticleAI.modules.actualityResolver.DAO.interfaces.IActualityResolverDAO;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.actualityResolver.parser.ClassParser;
import com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests.SearchAPIService;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality.IActualityService;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ActualityService implements IActualityService {

    private final
    IActualityResolverDAO actualityResolverDAO;

    private final SearchAPIService searchAPIService;

    @Override
    public List<Actuality> getActuality(List<Class> classes) {
        List<Actuality> actualityPair = new ArrayList<>();
        classes.forEach(parsedClass ->
                actualityPair.add(new Actuality(parsedClass.getClassId(), searchAPIService.getSearchCount(parsedClass)))
        );
        actualityResolverDAO.save(actualityPair);
        return actualityPair;
    }
}
