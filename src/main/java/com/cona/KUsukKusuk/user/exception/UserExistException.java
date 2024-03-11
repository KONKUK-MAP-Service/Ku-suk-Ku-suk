package com.cona.KUsukKusuk.user.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Slf4j
public class UserExistException extends RuntimeException{
    private final HttpStatus httpStatus;

    public UserExistException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public UserExistException() {
        this(HttpExceptionCode.USER_EXIST);
    }


}
