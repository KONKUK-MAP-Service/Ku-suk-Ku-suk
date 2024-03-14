package com.cona.KUsukKusuk.profile.exception;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ImageUploadException extends RuntimeException{
    private final HttpStatus httpStatus;

    public ImageUploadException(HttpExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public ImageUploadException() {
        this(HttpExceptionCode.IMAGE_UPLOAD_FAILED);
    }


}
