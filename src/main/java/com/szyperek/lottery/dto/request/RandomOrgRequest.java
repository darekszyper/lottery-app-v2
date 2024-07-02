package com.szyperek.lottery.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RandomOrgRequest {
    private final String jsonrpc = "2.0";
    private final String method = "generateIntegers";
    private RandomOrgParams params;
    private int id;

    @Override
    public String toString() {
        return "\nRandomOrgRequest:\n{\n" +
                "\"jsonrpc\": \"" + jsonrpc + '\"' + ",\n" +
                "\"method\": " + method + '\"' + ",\n" +
                "\"params\": " + params +
                "\"id\": " + id +
                "\n}\n";
    }

    public void setParams(String apiKey, int amountOfNumbers, int range) {
        this.params = new RandomOrgParams(apiKey, amountOfNumbers, range);
    }

    @Data
    @AllArgsConstructor
    private static class RandomOrgParams {
        private String apiKey;
        private int n;
        private final int min = 0;
        private int max;
        private final boolean replacement = false;

        @Override
        public String toString() {
            return "{\n" +
                    "   \"apiKey\": \"secret\""+ ",\n" +
                    "   \"n\": " + n + ",\n" +
                    "   \"min\": " + min + ",\n" +
                    "   \"max\": " + max + ",\n" +
                    "   \"replacement\": " + replacement + "\n" +
                    "},\n";
        }
    }
}

