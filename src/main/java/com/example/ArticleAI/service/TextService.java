package com.example.ArticleAI.service;

import com.example.ArticleAI.models.AppSettings;
import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.repository.AppSettingsRepository;
import com.example.ArticleAI.service.ApachePOI.POIArticleProcessService;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TextService {

    private final POIArticleProcessService articleProcessService;
    private final AppSettingsRepository appSettingsRepository;


    /**
     * @param article file
     * @param yake    yake meta params
     * @return Full yake article
     */
    @SneakyThrows
    public ArticleYake getYakeTextFromPDF(MultipartFile article, ArticleYake yake) {
        final AppSettings appSettings = appSettingsRepository.getAll();
        ArticleYake.ArticleYakeBuilder articleYakeBuilder = ArticleYake.builder();
        PdfReader reader = new PdfReader(article.getInputStream());
        StringBuilder articleText = new StringBuilder();
        for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
            TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
            articleText.append("\n").append(PdfTextExtractor.getTextFromPage(reader, i, strategy));
        }
        reader.close();
        try {
            articleProcessService.setArticleText(articleText.toString());
            articleYakeBuilder
                    .text(articleProcessService.getArticleParsedText())
                    .deduplication_algo(appSettings.getYakeParams().getDeduplication_algo())
                    .deduplication_thresold(appSettings.getYakeParams().getDeduplication_thresold())
                    .max_ngram_size(appSettings.getYakeParams().getMax_ngram_size())
                    .number_of_keywords(appSettings.getYakeParams().getNumber_of_keywords())
                    .windowSize(appSettings.getYakeParams().getWindowSize())
                    .language(appSettings.getYakeParams().getLanguage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return yake;
    }
}
