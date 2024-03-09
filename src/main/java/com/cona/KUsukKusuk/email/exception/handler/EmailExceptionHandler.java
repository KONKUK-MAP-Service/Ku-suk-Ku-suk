package com.cona.KUsukKusuk.email.exception.handler;


import com.cona.KUsukKusuk.email.exception.EmailNotSendException;
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
public class EmailExceptionHandler {
    @ExceptionHandler(EmailNotSendException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<ErrorResponse> EmailnotSendExceptionHandler(EmailNotSendException e) {
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
}
