package com.szyperek.lottery.service.impl;

import com.google.gson.Gson;
import com.szyperek.lottery.dto.request.RandomOrgRequest;
import com.szyperek.lottery.dto.response.RandomOrgResponse;
import com.szyperek.lottery.service.RandomizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RandomOrgServiceImpl implements RandomizeService {

    @Value("${RANDOM_API_KEY}")
    private String API_KEY;
    private static final String API_URL = "https://api.random.org/json-rpc/4/invoke";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson;

    @Override
    public List<Integer> randomize(int range, int amountOfNumbers) {
        RandomOrgRequest request = buildRequest(amountOfNumbers, range);

        log.info("getResponse(" + API_URL + ", " + request + ")");
        String response = getResponse(request);

        RandomOrgResponse randomOrgResponse = convert(response);
        log.info("getResponse(...) = (" + randomOrgResponse + ")");

        if (response == null || randomOrgResponse.getResult() == null) {
            return null;
        }
        return randomOrgResponse.getResult().getRandom().getData();
    }

    private RandomOrgRequest buildRequest(int amountOfNumbers, int range) {
        RandomOrgRequest request = new RandomOrgRequest();
        request.setParams(API_KEY, amountOfNumbers, range);
        return request;
    }

    private RandomOrgResponse convert(String body) {
        return gson.fromJson(body, RandomOrgResponse.class);
    }

    private String getResponse(RandomOrgRequest request) {
        String jsonRequest = gson.toJson(request);
        RequestBody requestBody = RequestBody.create(jsonRequest, JSON);

        Request httpRequest = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(httpRequest).execute()) {
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            log.error("Cannot connect to Random.org API, system will use local randomize() implementation");
        }
        return null;
    }
}
