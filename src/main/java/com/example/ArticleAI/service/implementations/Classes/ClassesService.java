package com.example.ArticleAI.service.implementations.Classes;

import com.example.ArticleAI.modules.classesResolver.models.Class;
import com.example.ArticleAI.service.interfaces.Classes.IClassesService;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassesService implements IClassesService {
    @Override
    public List<Class> parseClasses(String ClassesJSON) {
        List<Class> result = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(ClassesJSON);
        for (int i = 0; i < jsonArray.length(); i++){
            result.add(
                    new Class(
                            jsonArray.getJSONObject(i).getInt("classId"),
                            jsonArray.getJSONObject(i).getInt("keywordId"),
                            jsonArray.getJSONObject(i).getString("keywordText"),
                            (float) jsonArray.getJSONObject(i).getDouble("classWeight"),
                            jsonArray.getJSONObject(i).getString("className")
                    )
            );
        }
        return result;
    }
}
