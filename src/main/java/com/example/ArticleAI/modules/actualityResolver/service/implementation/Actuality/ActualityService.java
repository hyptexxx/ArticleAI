package com.example.ArticleAI.modules.actualityResolver.service.implementation.Actuality;

import com.example.ArticleAI.dto.ActualityDTO;
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
public class ActualityService implements IActualityService {

    private final
    IActualityResolverDAO actualityResolverDAO;

    private final SearchAPIService searchAPIService;
    @Override
    public List<ActualityDTO> getActuality(List<Class> classes) {
        List<ActualityDTO> actualityPair = new ArrayList<>();
        List<Actuality> actuality = new ArrayList<>();
        classes.forEach(parsedClass -> {
                    Long searchCount = searchAPIService.getSearchCount(parsedClass);
                    actualityPair.add(ActualityDTO.builder()
                            .actuality(searchCount)
                            .keywordText(parsedClass.getKeywordText())
                            .className(parsedClass.getClassName())
                            .build());
                    actuality.add(Actuality.builder()
                            .actuality(searchCount)
                            .classId(parsedClass.getClassId())
                            .build());
                }
        );

        try {
            actualityResolverDAO.save(actuality);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }
        return actualityPair;
    }
}
