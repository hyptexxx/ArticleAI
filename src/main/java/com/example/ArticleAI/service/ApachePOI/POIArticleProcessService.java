package com.example.ArticleAI.service.ApachePOI;

import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class POIArticleProcessService {

    private String parsedArticleText;

    /**
     * @return article name
     * @throws ParseException parse exception, if text are not equals to following regex
     */
    public String getArticleName() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @return article keywords
     * @throws ParseException ParseException parse exception, if text are not equals to following regex
     */
    public String getArticleKeyWords() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @return article references
     * @throws ParseException ParseException parse exception, if text are not equals to following regex
     */
    public String getArticleReferences() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @return article text
     * @throws ParseException ParseException parse exception, if text are not equals to following regex
     */
    public String getArticleText() throws ParseException {
        return parsedArticleText;
    }

    /**
     * @param articleText parsed article text
     */
    public void setArticleText(String articleText) {
        parsedArticleText = articleText.toLowerCase();
    }

    /**
     * @return parsed article text
     * @throws ParseException ParseException, when one of used method are thrown it
     */
    public String getArticleParsedText() throws ParseException {
        this.removeEng();
        return this.parsedArticleText;
    }

    public void removeEng() throws ParseException {
        this.parsedArticleText = this.parsedArticleText.replaceAll("[a-z]"," ");
    }
}
