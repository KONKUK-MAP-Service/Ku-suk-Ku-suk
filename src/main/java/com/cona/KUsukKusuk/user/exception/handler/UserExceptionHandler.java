package com.cona.KUsukKusuk.user.exception.handler;

import com.cona.KUsukKusuk.global.response.ErrorResponse;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.user.exception.NickNameAlreadyExistException;
import com.cona.KUsukKusuk.user.exception.PasswordNotMatchException;
import com.cona.KUsukKusuk.user.exception.UserExistException;
import com.cona.KUsukKusuk.user.exception.UserIdAlreadyExistException;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
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
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HttpResponse<ErrorResponse> userNotFoundExceptionHandler(UserNotFoundException e) {
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
    @ExceptionHandler(PasswordNotMatchException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public HttpResponse<ErrorResponse> passwordNotMatchExceptionHandler(PasswordNotMatchException e) {
        return HttpResponse.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
    @ExceptionHandler(UserIdAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> userIdAlreadyExistExceptionHandler(UserIdAlreadyExistException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
    @ExceptionHandler(NickNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> nickNameAlreadyExistExceptionHandler(NickNameAlreadyExistException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> memberExistExceptionHandler(UserExistException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e.getHttpStatus(), e.getMessage()));
    }
}
