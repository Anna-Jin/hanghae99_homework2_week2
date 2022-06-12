package com.homework.homework_week2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_BLANK("ERROR_CODE_0001","No blank allowed.")
    , MIN_VALUE("ERROR_CODE_0002", "Must be at least minimum length.")
    , INVALID_PATTERN("ERROR_CODE_0003", "Invalid pattern included.")
    ;

    private String code;

    private String description;

}
