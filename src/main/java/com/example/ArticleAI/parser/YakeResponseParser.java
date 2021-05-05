package com.example.ArticleAI.parser;

import com.example.ArticleAI.models.YakeResponse;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class YakeResponseParser {
    public static Optional<List<YakeResponse>> parse(String classes) {
        List<YakeResponse> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(classes);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(
                        YakeResponse.builder()
                                .ngram(jsonArray.getJSONObject(i).getString("ngram"))
                                .score(jsonArray.getJSONObject(i).getDouble("score"))
                                .build()
                );
            }
        } catch (Exception e){
            return Optional.empty();
        }

        return Optional.of(result);
    }
}
