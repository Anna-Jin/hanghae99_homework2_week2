package com.homework.homework_week2.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode {

    ERROR_USER_NOT_EXISTS(HttpStatus.FORBIDDEN, "로그인이 필요합니다"),
    NULL_FILE(HttpStatus.BAD_REQUEST, "파일을 업로드해주세요")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
