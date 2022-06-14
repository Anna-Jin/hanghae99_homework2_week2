package com.homework.homework_week2.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
    NULL_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "file is required")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}