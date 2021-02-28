package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.YakeResponse;
import org.json.JSONArray;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YakeMapper {
    public static Optional<List<YakeResponse>> parse(String classes) throws ParseException {
        List<YakeResponse> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(classes);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(
                        YakeResponse.builder()
                                .ngram(jsonArray.getJSONObject(i).getString("ngram"))
                                .score(jsonArray.getJSONObject(i).getInt("score"))
                                .build()
                );
            }
        } catch (Exception e){
            return Optional.empty();
        }

        return Optional.of(result);
    }
}
