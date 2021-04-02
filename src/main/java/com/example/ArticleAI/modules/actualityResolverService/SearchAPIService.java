package com.example.ArticleAI.modules.actualityResolverService;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class SearchAPIService {
    @SneakyThrows
    public Optional<Long> getSearchCount(String className) {
        long searchCount;
        Document doc = Jsoup.connect("https://www.google.com/search?q=" + className).get();

        String searchResult = doc.select("#result-stats").first().ownText();
        if (!StringUtils.isBlank(searchResult)) {
            searchCount = Long.parseLong(Stream.of(searchResult.split(""))
                    .filter(str -> Character.isDigit(searchResult.charAt(searchResult.indexOf(str))))
                    .map(StringBuilder::new)
                    .collect(Collectors.joining("")));
            return Optional.of(searchCount);
        } else {
            return Optional.empty();
        }
    }
}