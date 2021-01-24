package com.example.ArticleAI.service.implementations.ApachePOI;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIArticleProcessService;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.List;

@Service
public class POIService implements IPOIService {

    private final
    IPOIArticleProcessService articleProcessService;

    public POIService(IPOIArticleProcessService articleProcessService) {
        this.articleProcessService = articleProcessService;
    }

    /**
     * @param article file
     * @param yake    yake meta params
     * @return full yake model
     */
    @Override
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
