package com.capsule.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private int statusNum;
    private String errorCode;
    private String errorMessage;

    public static ExceptionResponse createExceptionResponse(ExceptionMessage exceptionMessage){
        return new ExceptionResponse(exceptionMessage.getStatusNum(), exceptionMessage.getErrorCode(),exceptionMessage.getErrorCode());
    }
}
