package com.example.ArticleAI.modules.actualityResolver.parser;

import com.example.ArticleAI.modules.classesResolver.models.Class;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ClassParser {
    public List<Class> getClassesFromJSON (String classes) {
        List<Class> result = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(classes);
        for (int i = 0; i < jsonArray.length(); i++){
            result.add(
                    new Class(jsonArray.getJSONObject(i).getInt("classId"),
                            0,
                            "",
                            (float) 0.0,
                            ""
                    )
            );
        }
        return result;
    }
}
