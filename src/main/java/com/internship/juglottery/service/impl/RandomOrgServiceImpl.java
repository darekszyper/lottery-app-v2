//package com.internship.juglottery.service.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.internship.juglottery.service.RandomizeService;
//import com.manoelcampos.randomorg.RandomOrgClient;
//import io.github.cdimascio.dotenv.Dotenv;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.Random;
//
//@Service
//public class RandomOrgServiceImpl implements RandomizeService {
//
//    public static final String APIKEY_ENV_VAR_NAME = "RANDOM_ORG_API_KEY";
//    static final String JSONRPC_VERSION = "2.0";
//    private final String API_KEY;
//    private final HttpClient client;
//    private final ObjectMapper objectMapper;
//
//    /**
//     * Instantiates an object for sending requests to the random.org service
//     * to generate real random values.
//     * Reads the API Key from an environment variable with the name
//     * as indicated by {@link #APIKEY_ENV_VAR_NAME}.
//     */
//    public RandomOrgServiceImpl() {
//        this(Dotenv.load().get(APIKEY_ENV_VAR_NAME));
//    }
//
//    /**
//     * Instantiates an object for sending requests to the random.org service
//     * to generate real random values.
//     */
//    public RandomOrgServiceImpl(final String apiKey) {
//        objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        API_KEY = apiKey;
//        client = HttpClient.newBuilder().build();
//    }
//
//
//    public int[] generateIntegers(final int n) {
//        return generateIntegers(new GenerateIntegersRequestParams(n));
//    }
//
//
//    public int[] generateNonDuplicatedIntegers(final int n) {
//        return generateIntegers(new GenerateIntegersRequestParams(n, false));
//    }
//
//
//    public int[] generateIntegers(final int n, final int minValue, final int maxValue) {
//        return generateIntegers(new GenerateIntegersRequestParams(n, minValue, maxValue));
//    }
//
//
//    public int[] generateNonDuplicatedIntegers(final int n, final int minValue, final int maxValue) {
//        return generateIntegers(new GenerateIntegersRequestParams(n, minValue, maxValue, false));
//    }
//
//    private int[] generateIntegers(final GenerateIntegersRequestParams params) {
//        final var data = new GenerateIntegersRequestData(params.setApiKey(API_KEY));
//        try {
//            final var json = objectMapper.writeValueAsString(data);
//            final var req =
//                    HttpRequest
//                            .newBuilder(new URI(GenerateIntegersRequestData.API_PATH))
//                            .header("content-type", "application/json")
//                            .POST(HttpRequest.BodyPublishers.ofString(json))
//                            .build();
//
//            final String res = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
//            final var generateIntegersResponse = objectMapper.readValue(res, GenerateIntegersResponse.class);
//            return generateIntegersResponse.getResult().getRandom().getData();
//        } catch (URISyntaxException | InterruptedException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        final var randomService = new RandomOrgClient();
//        final int n = 4;
//        final int minValue = 1;
//        final int maxValue = 10;
//        System.out.printf(
//                "Generating %d real random integers from [%d ..%d] using %s%n",
//                n, minValue, maxValue, randomService.getClass().getSimpleName());
//        for (final int number : randomService.generateIntegers(n, minValue, maxValue)) {
//            System.out.println(number);
//        }
//    }
//
//    @Override
//    public int randomize(int numberOfParticipants) {
//        Random random = new Random();
//
//        return random.ints(0, numberOfParticipants)
//                .limit(3)
//                .sum() % numberOfParticipants;
//    }
//}
