package com.example.ArticleAI.modules.recomendationsResolver.parser;

import com.example.ArticleAI.modules.actualityResolver.models.Actuality;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ActualityParser {
    public List<Actuality> getActualityFromJSON (String classes) {
        List<Actuality> result = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(classes);
        for (int i = 0; i < jsonArray.length(); i++){
            result.add(
                    new Actuality(
                            jsonArray.getJSONObject(i).getInt("class"),
                            jsonArray.getJSONObject(i).getLong("actuality")
                    )
            );
        }
        return result;
    }
}
