package com.example.ArticleAI.modules.recomendationsResolver.service.implementations;

import com.example.ArticleAI.mappers.NlpFilterMapper;
import com.example.ArticleAI.models.NlpResponse;
import com.example.ArticleAI.models.YakeResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@Deprecated
public class NlpRequestService {
    public List<NlpResponse> doFilter(List<YakeResponse> yakeResponse) {
//        OkHttpClient client;
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(60, TimeUnit.SECONDS);
//        builder.readTimeout(60, TimeUnit.SECONDS);
//        builder.writeTimeout(60, TimeUnit.SECONDS);
//        client = builder.build();
//        Response response = null;
//        String responseBody = null;
//
//        final RequestBody body = RequestBody
//                .create(new Gson().toJson(yakeResponse), MediaType.parse("application/json; charset=utf-8"));
//        final Request request = new Request.Builder()
//                .url("http://localhost:8081/api/v1/analyse")
//                .post(body)
//                .build();
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            switch (response != null ? response.code() : 404) {
//                case 404:
//                    responseBody = null;
//                    log.error("nlp response 404");
//                    break;
//
//                case 500:
//                    responseBody = null;
//                    log.error("nlp response 500");
//                    break;
//
//                case 403:
//                    responseBody = null;
//                    log.error("nlp response 403");
//                    break;
//
//                case 200:
//                    log.info("nlp response 200");
//                    responseBody = response.body().string();
//                    break;
//
//                default: // default branch
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new NlpFilterMapper().parse(responseBody);
        return null;
    }
}
