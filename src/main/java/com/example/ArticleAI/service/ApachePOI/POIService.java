package com.example.ArticleAI.service.ApachePOI;

import com.example.ArticleAI.models.AppSettings;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.repository.AppSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class POIService {

    private final POIArticleProcessService articleProcessService;
    private final AppSettingsRepository appSettingsRepository;

    /**
     * @param article file
     * @param yake    yake meta params
     * @return full yake model
     */
    public ArticleYake getArticleYakeText(File article, ArticleYake yake) {
        final AppSettings appSettings = appSettingsRepository.getAll();
        ArticleYake.ArticleYakeBuilder articleYakeBuilder = ArticleYake.builder();
        String articleText = null;
        try {
            final FileInputStream fis = new FileInputStream(article.getAbsolutePath());
            switch (FilenameUtils.getExtension(article.getAbsolutePath())) {
                case "doc":
                    try {
                        HWPFDocument doc = new HWPFDocument(fis);
                        WordExtractor extractor = new WordExtractor(doc);
                        articleText = Arrays.toString(extractor.getParagraphText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "docx":
                    try {
                        XWPFDocument doc = new XWPFDocument(fis);
                        XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
                        articleText = xwpfWordExtractor.getText();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    // do nothing
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (!StringUtils.isBlank(articleText)) {
                articleProcessService.setArticleText(articleText);
                articleYakeBuilder
                        .text(articleProcessService.getArticleParsedText())
                        .deduplication_algo(appSettings.getYakeParams().getDeduplication_algo())
                        .deduplication_thresold(appSettings.getYakeParams().getDeduplication_thresold())
                        .max_ngram_size(appSettings.getYakeParams().getMax_ngram_size())
                        .number_of_keywords(appSettings.getYakeParams().getNumber_of_keywords())
                        .windowSize(appSettings.getYakeParams().getWindowSize())
                        .language(appSettings.getYakeParams().getLanguage());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return articleYakeBuilder.build();
    }
}
