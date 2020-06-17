package com.example.ArticleAI.service.interfaces.ApachePOI;

import java.text.ParseException;

public interface IPOIArticleProcessService {
    /**
     * @return article name
     */
    String getArticleName() throws ParseException;

    /**
     * @return article keywords
     */
    String getArticleKeyWords() throws ParseException;

    /**
     * @return articleReferences
     */
    String getArticleReferences() throws ParseException;

    /**
     * @return articleReferences
     */
    String getArticleText() throws ParseException;

    /**
     * @param articleText parsed article text
     */
    void setArticleText(String articleText);

    /**
     * @return parsed article text
     */
    String getArticleParsedText() throws ParseException;;
}
