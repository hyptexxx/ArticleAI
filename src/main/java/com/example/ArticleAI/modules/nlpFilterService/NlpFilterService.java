package com.example.ArticleAI.modules.nlpFilterService;

import com.example.ArticleAI.exception.NlpResponseBadRequestException;
import com.example.ArticleAI.exception.NlpResponseInternalServerErrorException;
import com.example.ArticleAI.exception.NlpResponseNotFoundException;
import com.example.ArticleAI.exception.NlpResponseUnauthorizedException;
import com.example.ArticleAI.models.NlpResponse;
import com.example.ArticleAI.models.YakeResponse;
import com.example.ArticleAI.parser.NlpFilterParser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class NlpFilterService {
    @Value("${module.url}")
    private String moduleUrl;

    public List<NlpResponse> doFilter(List<YakeResponse> yakeResponse) throws ParseException, NlpResponseBadRequestException {
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        client = builder.build();
        Response response = null;
        String responseBody = null;

        final RequestBody body = RequestBody
                .create(new Gson().toJson(yakeResponse), MediaType.parse("application/json; charset=utf-8"));
        final Request request = new Request.Builder()
                .url("http://" + moduleUrl + ":8081/api/v1/analyse")
                .post(body)
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (response != null ? response.code() : 404) {
            case 404:
                log.error("nlp response 404");
                throw new NlpResponseNotFoundException("nlp response 404");

            case 500:
                log.error("nlp response 500");
                throw new NlpResponseInternalServerErrorException("nlp response 500");

            case 403:
                log.error("nlp response 403");
                throw new NlpResponseUnauthorizedException("nlp response 403");

            case 200:
                log.info("nlp response 200");
                try {
                    responseBody = response.body().string();
                } catch (IOException e) {
                    throw new NlpResponseBadRequestException("Nlp response is not parsable");
                }
                break;

            default: // default branch
        }

        return NlpFilterParser.parse(responseBody)
                .orElseThrow(() -> new ParseException("Nlp response is not parsable", 0));
    }
}
