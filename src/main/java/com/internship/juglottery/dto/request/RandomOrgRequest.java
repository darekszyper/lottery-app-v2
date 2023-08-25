package com.internship.juglottery.dto.request;

import lombok.Data;

@Data
public class RandomOrgRequest {
    private final String jsonrpc = "2.0";
    private final String method = "generateIntegers";
    private RandomOrgParams params;
    private final int id = 1;

    @Override
    public String toString() {
        return "\nRandomOrgRequest:\n{\n" +
                "\"jsonrpc\": \"" + jsonrpc + '\"' + ",\n" +
                "\"method\": " + method + '\"' + ",\n" +
                "\"params\": " + params +
                "\"id\": " + id +
                "\n}\n";
    }

    @Data
    public static class RandomOrgParams {
        private String apiKey;
        private int n;
        private int min;
        private int max;
        private boolean replacement;

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

