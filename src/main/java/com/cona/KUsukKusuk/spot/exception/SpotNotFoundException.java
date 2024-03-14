package com.cona.KUsukKusuk.spot.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class SpotNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public SpotNotFoundException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public SpotNotFoundException() {
        this(HttpExceptionCode.IMAGE_UPLOAD_FAILED);
    }


}
