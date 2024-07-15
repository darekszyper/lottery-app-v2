package com.szyperek.lottery.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public enum BusinessErrorCodes {
    NEW_PASSWORD_DOES_NOT_MATCH(300, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(301, FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(302, FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(303, FORBIDDEN, "Login and / or Password is incorrect"),
    ;

    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = status;
    }
}
