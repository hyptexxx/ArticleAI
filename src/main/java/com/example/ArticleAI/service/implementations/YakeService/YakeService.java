package com.example.ArticleAI.service.implementations.YakeService;

import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.service.interfaces.YakeService.IYakeService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YakeService implements IYakeService {
    @Override
    public List<YakeResponse> parseYakeResponseJSON(String yakeResponseJSON) {
        List<YakeResponse> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(yakeResponseJSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(
                        new YakeResponse(
                                jsonArray.getJSONObject(i).getString("ngram"),
                                jsonArray.getJSONObject(i).getDouble("score")
                        )
                );
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
