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
    public String sendRequest(ArticleYake articleYake) throws IOException {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        client  = builder.build();
        final RequestBody body = RequestBody.create(new Gson().toJson(articleYake), MediaType.parse("application/json; charset=utf-8"));
        final Request request = new Request.Builder()
                .url("http://10.10.1.30:5000/yake/")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null ? response.body().string() : "";
    }

    @Override
    public String sendRequestToYandex(String searchQuery) throws IOException {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        client  = builder.build();
        final RequestBody body = RequestBody.create("?user=ilya-2379&key=03.91546018:0660c5256a375c6868f3be3145ae13a3&query=%D0%90%D1%83%D0%B4%D0%B8&l10n=ru&sortby=tm.order%3Dascending&filter=strict&groupby=attr%3D%22%22.mode%3Dflat.groups-on-page%3D10.docs-in-group%3D1", MediaType.parse("application/json; charset=utf-8"));
        final Request request = new Request.Builder()
                .url("https://yandex.ru/search/xml")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null ? response.body().string() : "";
    }
}
