package com.example.ArticleAI.mappers;

import com.example.ArticleAI.models.NlpResponse;
import org.json.JSONArray;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NlpFilterMapper {
    public static Optional<List<NlpResponse>> parse(String nlpResponse) throws ParseException {
        List<NlpResponse> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(nlpResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(
                        NlpResponse.builder()
                                .ngram(jsonArray.getJSONObject(i).getString("ngram"))
                                .value(jsonArray.getJSONObject(i).getDouble("value"))
                                .isGood(jsonArray.getJSONObject(i).getInt("isGood"))
                                .build()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException("Nlp response is not parsable", 0);
        }

        return Optional.of(result);
    }
}
