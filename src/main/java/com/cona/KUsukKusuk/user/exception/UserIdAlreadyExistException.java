package com.cona.KUsukKusuk.user.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserIdAlreadyExistException extends RuntimeException{
    private final HttpStatus httpStatus;

    public UserIdAlreadyExistException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public UserIdAlreadyExistException() {
        this(HttpExceptionCode.USERID_EXIST);
    }
}
