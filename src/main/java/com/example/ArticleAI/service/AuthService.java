package com.example.ArticleAI.service;

import com.example.ArticleAI.client.ClientConfigFlow;
import com.example.ArticleAI.dto.AuthResponse;
import com.example.ArticleAI.models.ErrorsToClient;
import com.example.ArticleAI.models.UtmoUser;
import okhttp3.*;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    public String sendAuthRequest(String login, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("query", "AUTH")
                .addFormDataPart("login", login)
                .addFormDataPart("password", password)
                .build();
        String UT_MO_API_URL = "https://ies.unitech-mo.ru/api?token=e78a4a9c0b16dd06b0ebc4748345a144";
        Request request = new Request.Builder()
                .url(UT_MO_API_URL)
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response != null ? response.body().string() : null;
    }

    public AuthResponse<UtmoUser> startParseJSON(String json) {
        final AuthResponse<UtmoUser> authResponse = new AuthResponse<>();

        try {
            authResponse.setUser(getUser(json));
        } catch (Exception e) {
            final List<ErrorsToClient> errorsToClient= new ArrayList<>();

            errorsToClient.add(ErrorsToClient.builder()
                    .errorName("Ошибка аутентификации")
                    .message("Не удалось получить профиль клиента")
                    .cause(e.getMessage())
                    .build());

            authResponse.setErrorsToClient(errorsToClient);

        }

        return authResponse;
    }


    private UtmoUser getUser(String json) {
        final JsonReader jsonReader = Json.createReader(new StringReader(json));
        final JsonObject jsonObject = jsonReader.readObject();

        final UtmoUser utmoUser = UtmoUser.builder()
                .id(jsonObject.getInt("id"))
                .pin(jsonObject.getInt("pin"))
                .info(jsonObject.getString("info"))
                .success(jsonObject.getBoolean("success"))
                .fio(jsonObject.getString("fio"))
                .admin_auth(jsonObject.getInt("admin_auth"))
                .editor_auth(jsonObject.getInt("editor_auth"))
                .user_type(jsonObject.getInt("user_type"))
                .first_auth(jsonObject.getInt("first_auth"))
                .email(jsonObject.getString("email"))
                .allowed(jsonObject.getInt("allowed"))
                .avatar(
                        jsonObject.isNull("avatar")
                                ? ""
                                : jsonObject.getString("avatar")
                ).build();
        jsonReader.close();

        return utmoUser;
    }
}
