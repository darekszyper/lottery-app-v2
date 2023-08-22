package com.internship.juglottery.exception;

public class LotteryIsFinishedException extends RuntimeException{

    public LotteryIsFinishedException(String message) {
        super(message);
    }
}
