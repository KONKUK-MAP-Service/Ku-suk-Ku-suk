package com.cona.KUsukKusuk.like.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class LikeException extends RuntimeException{
    private final HttpStatus httpStatus;

    public LikeException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public LikeException() {
        this(HttpExceptionCode.LIKE_ERR);
    }

}
