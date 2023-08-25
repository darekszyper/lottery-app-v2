package com.internship.juglottery.service.impl;

import com.google.gson.Gson;
import com.internship.juglottery.dto.request.RandomOrgRequest;
import com.internship.juglottery.dto.response.RandomOrgResponse;
import okhttp3.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RandomOrgServiceImpl {

    @Value("${RANDOM_API_KEY}")
    private String API_KEY;
    private static final String API_URL = "https://api.random.org/json-rpc/4/invoke";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public RandomOrgResponse randomize(int range, int amountOfNumbers) {
        try {
            RandomOrgRequest request = buildRequest(range, amountOfNumbers);
            String response = getResponse(API_URL, request);
            RandomOrgResponse randomOrgResponse = convert(response);
            log.info("Random numbers: " + randomOrgResponse);
            return randomOrgResponse;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public RandomOrgRequest buildRequest(int range, int amountOfNumbers) {
        RandomOrgRequest.RandomOrgParams params = new RandomOrgRequest.RandomOrgParams();
        params.setApiKey(API_KEY);
        params.setN(amountOfNumbers);
        params.setMin(1);
        params.setMax(range);
        params.setReplacement(false);

        RandomOrgRequest request = new RandomOrgRequest();
        request.setParams(params);

        return request;
    }

    public RandomOrgResponse convert(String body) {
        return gson.fromJson(body, RandomOrgResponse.class);
    }

    public String getResponse(String url, RandomOrgRequest request) throws IOException {
        log.info("getResponse(" + url + ", " + request + ")");

        String jsonRequest = gson.toJson(request);
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonRequest, JSON);

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(httpRequest).execute()) {
            String responseBody = response.body().string();
            log.info("getResponse(...) = " + responseBody);
            return responseBody;
        }
    }
}
