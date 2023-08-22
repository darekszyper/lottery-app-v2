package com.internship.juglottery.exception;

public class UniqueUserEmailException extends RuntimeException{

    public UniqueUserEmailException(String message) {
        super(message);
    }
}
