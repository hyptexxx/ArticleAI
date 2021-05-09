package com.example.ArticleAI.service.ApachePOI;

import com.example.ArticleAI.models.ArticleYake;
import com.google.common.base.CharMatcher;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Service
public class POIService {

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
