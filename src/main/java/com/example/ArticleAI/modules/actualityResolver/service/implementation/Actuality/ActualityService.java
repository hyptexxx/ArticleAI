package com.example.ArticleAI.modules.actualityResolver.service.implementation.Actuality;

import com.example.ArticleAI.dto.ActualityDTO;
import com.example.ArticleAI.models.ClassDistance;
import com.example.ArticleAI.modules.actualityResolver.DAO.interfaces.IActualityResolverDAO;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.actualityResolver.parser.ClassParser;
import com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests.SearchAPIService;
import com.example.ArticleAI.modules.actualityResolver.service.interfaces.Actuality.IActualityService;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Deprecated
public class ActualityService {
    @Deprecated
    private final SearchAPIService searchAPIService;
    @Deprecated
    public Long getActuality(String clazz) {
        return searchAPIService.getSearchCount(clazz);
    }

}