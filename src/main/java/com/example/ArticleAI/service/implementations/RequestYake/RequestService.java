package com.example.ArticleAI.service.implementations.RequestYake;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.google.gson.Gson;
import okhttp3.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class RequestService implements IRequestService {

    private final
    Logger logger;

    public RequestService(Logger logger) {
        this.logger = logger;
    }

    @Override
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
