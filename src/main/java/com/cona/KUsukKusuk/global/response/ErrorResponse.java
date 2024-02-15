package com.cona.KUsukKusuk.global.response;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import org.springframework.http.HttpStatus;

public record ErrorResponse (HttpStatus errorCode,String message){

    public static ErrorResponse from(HttpExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getHttpStatus(), exceptionCode.getMessage());
    }

    public static ErrorResponse from(HttpExceptionCode exceptionCode, String errorMessage) {
        return new ErrorResponse(exceptionCode.getHttpStatus(), errorMessage);
    }
}
