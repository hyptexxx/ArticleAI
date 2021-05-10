package com.example.ArticleAI.parser;

import com.example.ArticleAI.models.AppSettings;
import com.example.ArticleAI.models.ArticleYake;
import lombok.experimental.UtilityClass;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Optional;

@UtilityClass
public class AppSettingsParser {
    public Optional<AppSettings> parse(String json) throws ParseException {
        AppSettings.AppSettingsBuilder appSettingsBuilder = AppSettings.builder();
        try {
            JSONObject jsonObject = new JSONObject(json);
            appSettingsBuilder.coefficient(jsonObject.getDouble("coefficient"))
                    .yakeParams(ArticleYake.builder()
                            .language(jsonObject.getJSONObject("yakeParams").getString("language"))
                            .max_ngram_size((float) jsonObject.getJSONObject("yakeParams").getDouble("max_ngram_size"))
                            .deduplication_thresold((float) jsonObject.getJSONObject("yakeParams").getDouble("deduplication_thresold"))
                            .deduplication_algo(jsonObject.getJSONObject("yakeParams").getString("deduplication_algo"))
                            .windowSize((float) jsonObject.getJSONObject("yakeParams").getDouble("windowSize"))
                            .number_of_keywords(jsonObject.getJSONObject("yakeParams").getInt("number_of_keywords"))
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseException("couldn't parse settings", 0);
        }

        return Optional.of(appSettingsBuilder.build());
    }
}
