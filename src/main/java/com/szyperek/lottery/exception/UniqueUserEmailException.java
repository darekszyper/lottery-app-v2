package com.szyperek.lottery.exception;

public class UniqueUserEmailException extends RuntimeException{

    public UniqueUserEmailException(String message) {
        super(message);
    }
}
