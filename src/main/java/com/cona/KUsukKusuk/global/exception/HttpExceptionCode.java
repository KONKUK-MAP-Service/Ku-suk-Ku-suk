package com.cona.KUsukKusuk.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HttpExceptionCode {



    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST,  "올바르지 않은 값이 전달되었습니다."),


    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT를 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    USERID_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 ID의 유저 정보를 찾을수 없습니다."),
    EMAIL_USER_NOT_EQUAL(HttpStatus.NOT_FOUND,"해당이메일과 사용자 정보가 일치하지 않습니다."),

    EMAIL_NOT_SEND(HttpStatus.NOT_FOUND,"이메일이 전송에 실패하였습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
