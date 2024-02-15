package com.cona.KUsukKusuk.global.exception;

public class ApplicationException extends RuntimeException{

    private final HttpExceptionCode exceptionCode;

    public ApplicationException(HttpExceptionCode exceptionCode) {

        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ApplicationException(HttpExceptionCode exceptionCode, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = exceptionCode;
    }
}
