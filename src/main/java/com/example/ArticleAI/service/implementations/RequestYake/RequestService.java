package com.example.ArticleAI.service.implementations.RequestYake;

import com.example.ArticleAI.models.ArticleYake;
import com.example.ArticleAI.service.interfaces.RequestYake.IRequestService;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RequestService implements IRequestService {
    @Override
    public String sendRequest(ArticleYake articleYake) throws IOException {
        final RequestBody body = RequestBody.create(new Gson().toJson(articleYake), MediaType.parse("application/json; charset=utf-8"));
        final Request request = new Request.Builder()
                .url("http://10.10.1.30:5000/yake/")
                .post(body)
                .build();
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null ? response.body().string() : "";
    }
}
