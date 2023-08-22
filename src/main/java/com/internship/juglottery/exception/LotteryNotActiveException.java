package com.internship.juglottery.exception;

public class LotteryNotActiveException extends RuntimeException {

    public LotteryNotActiveException(String message) {
        super(message);
    }
}
