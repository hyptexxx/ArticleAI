package com.example.ArticleAI.service.implementations.IText;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIArticleProcessService;
import com.example.ArticleAI.service.interfaces.IText.IITextService;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@Service
public class ITextService implements IITextService {

    private final
    IPOIArticleProcessService articleProcessService;

    public ITextService(IPOIArticleProcessService articleProcessService) {
        this.articleProcessService = articleProcessService;
    }


    /**
     * @param article file
     * @param yake yake meta params
     * @return Full yake article
     */
    @SneakyThrows
    @Override
    public ArticleYake getYakeTextFromPDF(MultipartFile article, ArticleYake yake) {
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
                    .deduplication_algo(yake.getDeduplication_algo())
                    .deduplication_thresold(yake.getDeduplication_thresold())
                    .max_ngram_size(yake.getMax_ngram_size())
                    .number_of_keywords(yake.getNumber_of_keywords())
                    .windowSize(yake.getWindowSize())
                    .language(yake.getLanguage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return yake;
    }
}
