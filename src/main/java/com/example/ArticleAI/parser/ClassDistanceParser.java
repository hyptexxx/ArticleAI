package com.example.ArticleAI.parser;

import com.example.ArticleAI.models.ClassDistance;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class ClassDistanceParser {
    public static Optional<List<ClassDistance>> parse(String nlpResponse) throws ParseException {
        List<ClassDistance> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(nlpResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(
                        ClassDistance.builder()
                                .distance(jsonArray.getJSONObject(i).getDouble("distance"))
                                .keyword(jsonArray.getJSONObject(i).getString("keyword"))
                                .className(jsonArray.getJSONObject(i).getString("className"))
                                .build()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException("Nlp module has return not parsable json value", 0);
        }

        return Optional.of(result);
    }
}
