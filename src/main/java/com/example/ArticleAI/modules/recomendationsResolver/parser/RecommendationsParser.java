package com.example.ArticleAI.modules.recomendationsResolver.parser;

import com.example.ArticleAI.modules.recomendationsResolver.models.Recommendation;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RecommendationsParser {
    public static List<Recommendation> parse(String classes) {
        List<Recommendation> result = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(classes);

        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(Recommendation.builder()
                    .recommendationText(jsonArray.getJSONObject(i).getString("text"))
                    .build());
        }

        return result;
    }
}
