package com.example.ArticleAI.service.implementations.ApachePOI;

import com.example.ArticleAI.service.interfaces.ApachePOI.IPOIArticleProcessService;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class POIArticleProcessService implements IPOIArticleProcessService {

    private String parsedArticleText;

    /**
     * @return article name
     * @throws ParseException parse exception, if text are not equals to following regex
     */
    @Override
    public String getArticleName() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @return article keywords
     * @throws ParseException ParseException parse exception, if text are not equals to following regex
     */
    @Override
    public String getArticleKeyWords() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @return article references
     * @throws ParseException ParseException parse exception, if text are not equals to following regex
     */
    @Override
    public String getArticleReferences() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @return article text
     * @throws ParseException ParseException parse exception, if text are not equals to following regex
     */
    @Override
    public String getArticleText() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @param articleText parsed article text
     */
    @Override
    public void setArticleText(String articleText) {
        parsedArticleText = articleText.toLowerCase();
    }

    /**
     * @return parsed article text
     * @throws ParseException ParseException, when one of used method are thrown it
     */
    @Override
    public String getArticleParsedText() throws ParseException {
        String parsedArticleText;
        try {
            parsedArticleText = this.getArticleName() + "\n\n" +
                    this.getArticleKeyWords()  + "\n\n" +
                    this.getArticleText()  + "\n\n" +
                    this.getArticleReferences();
        } catch (ParseException e) {
            throw new ParseException("Can't parse article text", 1);
        }
        return this.parsedArticleText;
    }
}
