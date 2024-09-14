package com.capsule.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse extends RuntimeException {

    private ExceptionMessage exceptionMessage;

    public ErrorResponse(ExceptionMessage exceptionMessage){
        this.exceptionMessage = exceptionMessage;
    }
}
