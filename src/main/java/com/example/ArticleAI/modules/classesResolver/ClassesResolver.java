package com.example.ArticleAI.modules.classesResolver;

import com.example.ArticleAI.modules.classesResolver.exceptions.emptyKeywordListException.EmptyKeywordListException;
import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.modules.classesResolver.models.Keyword;
import com.example.ArticleAI.modules.classesResolver.service.ClassesResolverService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Setter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassesResolver {
    private List<String> keyWords;
    private Integer articleId;

    private final ClassesResolverService classesResolverService;

    public List<Class> resolve() throws EmptyKeywordListException {
        List<Keyword> existingKeywordsList = classesResolverService.getExistingKeywordsList();
        List<Class> existingClassList;
        List<Class> resultClassList = new ArrayList<>();

        for (String keyword : this.keyWords) {
            if (classesResolverService.isKeywordExistsInExistingClassesList(keyword, existingKeywordsList)) {
                existingClassList = classesResolverService.getExistingClassList(keyword);
                resultClassList = ListUtils.union(resultClassList, processClass(existingClassList));
            } else {
                classesResolverService.saveNewKeyword(keyword, articleId);
            }
        }

        return resultClassList;
    }

    private static List<Class> processClass(List<Class> existingClassList) {
        List<Class> resultClassList = new ArrayList<>();
        for (Class existingClass : existingClassList) {
            if (existingClass.getClassWeight() != 0) {
                if (!resultClassList.contains(existingClass)) {
                    resultClassList.add(existingClass);
                } else {
                    float oldWeight = existingClass.getClassWeight();
                    resultClassList.get(resultClassList.indexOf(existingClass)).setClassWeight(oldWeight + oldWeight);
                }
            }
        }
        return resultClassList;
    }
}
