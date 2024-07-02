package com.szyperek.lottery.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RandomOrgResponse {
    private String jsonrpc;
    private RandomOrgResult result;
    private int id;

    @Override
    public String toString() {
        return "\nRandomOrgResponse:\n{\n" +
                "\"jsonrpc\": \"" + jsonrpc + '\"' + ",\n" +
                "\"result\": " + result +
                "\"id\": " + id +
                "\n}\n";
    }

    @Data
    public static class RandomOrgResult {
        private RandomData random;
        private int bitsUsed;
        private int bitsLeft;
        private int requestsLeft;
        private int advisoryDelay;

        @Override
        public String toString() {
            return "{\n" +
                    "   \"random\"" + random +
                    "   \"bitsUsed\": " + bitsUsed + ",\n" +
                    "   \"bitsLeft\": " + bitsLeft + ",\n" +
                    "   \"requestsLeft\": " + requestsLeft + ",\n" +
                    "   \"advisoryDelay\": " + advisoryDelay +
                    "\n},\n";
        }

        @Data
        public static class RandomData {
            private List<Integer> data;
            private String completionTime;

            @Override
            public String toString() {
                return "{\n" +
                        "       \"data\": " + data + ",\n" +
                        "       \"completionTime\": \"" + completionTime + '\"' + "\n" +
                        "   },\n";
            }
        }
    }
}

