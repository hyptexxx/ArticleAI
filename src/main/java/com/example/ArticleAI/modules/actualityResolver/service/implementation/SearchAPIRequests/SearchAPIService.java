package com.example.ArticleAI.modules.actualityResolver.service.implementation.SearchAPIRequests;

import com.example.ArticleAI.modules.classesResolver.models.Class;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SearchAPIService {
    @SneakyThrows
    public Long getSearchCount(String className) {
        long searchCount = 0L;
        Document doc = Jsoup.connect("https://www.google.com/search?q=" + className).get();

        String searchResult = doc.select("#result-stats").first().ownText();
        if (!StringUtils.isBlank(searchResult)) {
            searchCount = Long.parseLong(Stream.of(searchResult.split(""))
                    .filter(str -> Character.isDigit(searchResult.charAt(searchResult.indexOf(str))))
                    .map(StringBuilder::new)
                    .collect(Collectors.joining("")));
        }

        return searchCount;
    }
}