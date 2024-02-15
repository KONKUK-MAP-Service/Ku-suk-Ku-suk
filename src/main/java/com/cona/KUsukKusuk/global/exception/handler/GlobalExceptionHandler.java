package com.cona.KUsukKusuk.global.exception.handler;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.exception.custom.BuisnessException.BusinessException;
import com.cona.KUsukKusuk.global.response.ErrorResponse;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse<ErrorResponse> businessExceptionHandle(BusinessException e) {
        log.warn("businessException : {}", e);
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> allUncaughtHandle(Exception e) {
        log.error("allUncaughtHandle : {}", e);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected HttpResponse<ErrorResponse> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidException : {}", e);

        BindingResult bindingResult = e.getBindingResult();

        String errorMessage = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining());

        return HttpResponse.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.from(HttpExceptionCode.UNEXPECTED_EXCEPTION.getHttpStatus(), errorMessage));
    }

}
