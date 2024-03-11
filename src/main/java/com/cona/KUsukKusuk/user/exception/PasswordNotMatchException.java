package com.cona.KUsukKusuk.user.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Slf4j
public class PasswordNotMatchException extends RuntimeException{
    private final HttpStatus httpStatus;

    public PasswordNotMatchException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public PasswordNotMatchException() {
        this(HttpExceptionCode.PASSWORD_NOT_MATCH);
    }
}
