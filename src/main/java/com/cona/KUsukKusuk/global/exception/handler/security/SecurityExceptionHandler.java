package com.cona.KUsukKusuk.global.exception.handler.security;

import com.cona.KUsukKusuk.global.exception.custom.security.IncorrectRefreshTokenException;
import com.cona.KUsukKusuk.global.exception.custom.security.RefreshTokenNotFoundException;
import com.cona.KUsukKusuk.global.exception.custom.security.SecurityJwtNotFoundException;
import com.cona.KUsukKusuk.global.response.ErrorResponse;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    @ExceptionHandler(SecurityJwtNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpResponse<ErrorResponse> securityExceptionHandler(SecurityJwtNotFoundException e) {
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));

    }
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpResponse<ErrorResponse> refreshTokenNotFoundExceptionHandler(RefreshTokenNotFoundException e) {
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
    @ExceptionHandler(IncorrectRefreshTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public HttpResponse<ErrorResponse> incorrectRefreshTokenExceptionHandler(IncorrectRefreshTokenException e) {
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
}