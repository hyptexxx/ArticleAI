package com.example.ArticleAI.service.ApachePOI;

import com.example.ArticleAI.models.ArticleYake;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;

@Service
public class POIService{

    private final
    POIArticleProcessService articleProcessService;

    public POIService(POIArticleProcessService articleProcessService) {
        this.articleProcessService = articleProcessService;
    }

    /**
     * @param article file
     * @param yake    yake meta params
     * @return full yake model
     */
    public ArticleYake getArticleYakeText(File article, ArticleYake yake) {
        ArticleYake.ArticleYakeBuilder articleYakeBuilder = ArticleYake.builder();
        String articleText = null;
        try {
            final FileInputStream fis = new FileInputStream(article.getAbsolutePath());
            POITextExtractor extractor = ExtractorFactory.createExtractor(fis);
            articleText = extractor.getText();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!StringUtils.isBlank(articleText)) {
                articleProcessService.setArticleText(articleText);
                articleYakeBuilder
                        .text(articleProcessService.getArticleParsedText())
                        .deduplication_algo(yake.getDeduplication_algo())
                        .deduplication_thresold(yake.getDeduplication_thresold())
                        .max_ngram_size(yake.getMax_ngram_size())
                        .number_of_keywords(yake.getNumber_of_keywords())
                        .windowSize(yake.getWindowSize())
                        .language(yake.getLanguage());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return articleYakeBuilder.build();
    }
}
