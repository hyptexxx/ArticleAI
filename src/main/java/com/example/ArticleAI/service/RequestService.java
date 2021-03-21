package com.example.ArticleAI.service;

import com.example.ArticleAI.models.ArticleYake;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RequestService {

    public String sendRequest(ArticleYake articleYake) {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        client  = builder.build();
        Response response = null;
        String responseBody = null;
        final RequestBody body = RequestBody.create(new Gson().toJson(articleYake), MediaType.parse("application/json; charset=utf-8"));
        final Request request = new Request.Builder()
                .url("http://10.10.1.28:5000/yake/")
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            responseBody = response != null ? response.body().string() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
}
