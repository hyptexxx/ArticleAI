package com.example.ArticleAI.service.interfaces.RequestYake;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import org.apache.commons.io.FileExistsException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public interface IRequestService {
    /**
     * @param articleYake yake model
     * @return request result
     * @throws IOException
     */
    String sendRequest(ArticleYake articleYake) throws IOException;


    /**
     * @param searchQuery search keyword
     * @return yandex search results
     * @throws IOException
     */
    String sendRequestToYandex(String searchQuery) throws IOException;
}
