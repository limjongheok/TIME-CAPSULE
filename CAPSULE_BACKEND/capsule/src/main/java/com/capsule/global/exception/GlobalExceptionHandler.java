package com.capsule.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorResponse.class)
    public ResponseEntity<?> handlerException(ErrorResponse e){
        ExceptionResponse exceptionResponse = ExceptionResponse.createExceptionResponse(e.getExceptionMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(e.getExceptionMessage().getStatusNum()));
    }
}
