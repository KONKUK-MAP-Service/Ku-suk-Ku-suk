package com.cona.KUsukKusuk.profile.exception.handler;


import com.cona.KUsukKusuk.global.response.ErrorResponse;
import com.cona.KUsukKusuk.profile.exception.ImageUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImageExceptionHandler {
    @ExceptionHandler(ImageUploadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> imageUploadFailedExceptionHandler(ImageUploadException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }

}
