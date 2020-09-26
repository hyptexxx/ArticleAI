package com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests;

import com.example.ArticleAI.modules.actualityResolver.service.interfaces.SearchAPIService.ISearchAPIService;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.springframework.stereotype.Service;

@Service
public class SearchAPIService implements ISearchAPIService {
    @Override
    public int getSearchCount(Class classes) {
        return 0;
    }
}
