package com.szyperek.lottery.exception;

public class LotteryNotActiveException extends RuntimeException {

    public LotteryNotActiveException(String message) {
        super(message);
    }
}
