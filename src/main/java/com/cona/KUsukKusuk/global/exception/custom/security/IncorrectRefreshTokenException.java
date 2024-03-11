package com.cona.KUsukKusuk.global.exception.custom.security;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class IncorrectRefreshTokenException extends RuntimeException{
    private final HttpStatus httpStatus;

    public IncorrectRefreshTokenException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public IncorrectRefreshTokenException() {
        this(HttpExceptionCode.INCORRECT_REFRESH_TOKEN);
    }
}
