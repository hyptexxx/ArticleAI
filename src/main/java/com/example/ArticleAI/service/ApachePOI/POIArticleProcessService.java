package com.example.ArticleAI.service.ApachePOI;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class POIArticleProcessService {
    private final List<String> BAN_LIST = ImmutableList.of("гбоу",
            "кафедр",
            "доцент",
            "преподавател",
            "институт",
            "профессор",
            "государственн",
            "общеобразовательн",
            "орлова",
            "наталья",
            "бюджетн",
            "учрежде",
            "бюджетн",
            "отчёт",
            "дата",
            "выда",
            "студе",
            "руководитель",
            "учебно-",
            "учебно-нау",
            "аспиран",
            "лаборатори",
            "преддиплом",
            "практик",
            "студента",
            "студентки",
            "област",
            "москов",
            "москв",
            "автор",
            "высше",
            "кандидат",
            "университет",
            "вера",
            "ольга",
            "владислав",
            "статья");
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
        parsedArticleText = articleText;
    }

    /**
     * @return parsed article text
     * @throws ParseException ParseException, when one of used method are thrown it
     */
    public String getArticleParsedText() throws ParseException {
        this.removeEng();
        this.removeBanWords();
        return this.parsedArticleText;
    }

    public void removeEng() throws ParseException {
        this.parsedArticleText = this.parsedArticleText.replaceAll("[a-z]", " ");
    }

    private void removeBanWords() {
        BAN_LIST.forEach(s -> this.parsedArticleText = this.parsedArticleText
                .toLowerCase()
                .replaceAll("\\S*" + s.toLowerCase() + "\\S*", ""));
    }
}
