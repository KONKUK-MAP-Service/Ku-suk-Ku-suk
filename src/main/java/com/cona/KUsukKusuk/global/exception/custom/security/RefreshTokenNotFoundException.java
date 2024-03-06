package com.cona.KUsukKusuk.global.exception.custom.security;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@Slf4j
public class RefreshTokenNotFoundException extends RuntimeException{
    private final HttpStatus httpStatus;

    public RefreshTokenNotFoundException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public RefreshTokenNotFoundException() {
        this(HttpExceptionCode.REFRESH_TOKEN_NOT_FOUND);
    }

}
