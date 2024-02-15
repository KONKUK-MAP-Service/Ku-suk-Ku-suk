package com.cona.KUsukKusuk.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HttpExceptionCode {
    UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,  "예상치 못한 오류가 발생했습니다."),
    SECURITY_AUTHORIZATION_EXCEPTION(HttpStatus.FORBIDDEN,  "사용자 인가에 실패했습니다."),
    SECURITY_AUTHENTICATION_EXCEPTION(HttpStatus.FORBIDDEN,  "사용자 인증에 실패했습니다."),
    INVALID_AUTHORIZATION_HEADER(
            HttpStatus.UNAUTHORIZED,  "Authorization Header가 유효하지 않습니다."),
    MEMBER_ID_NOT_FOUND_IN_ACCESS_TOKEN(
            HttpStatus.UNAUTHORIZED,  "memberId가 Token에 존재하지 않습니다."),
    AUTHORIZATION_TOKEN_EXPIRED_EXCEPTION(
            HttpStatus.UNAUTHORIZED, "Access 또는 Refresh 토큰의 기한이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,  "RefreshToken이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN(
            HttpStatus.BAD_REQUEST, "해당 Member에게 발급된 RefreshToken이 아닙니다."),

    JWT_EXPIRED(HttpStatus.UNAUTHORIZED,  "JWT 기한이 만료되었습니다."),
    JWT_MALFORMED(HttpStatus.UNAUTHORIZED,  "JWT가 손상되었습니다."),
    JWT_UNSUPPORTED(HttpStatus.UNAUTHORIZED,  "지원되지 않는 JWT 입니다."),
    JWT_INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED,  "signature가 유효하지 않습니다."),
    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT를 찾을 수 없습니다."),
    ID_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,  "authToken에서 idToken을 찾을 수 없습니다."),

    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST,  "올바르지 않은 값이 전달되었습니다.");




    private final HttpStatus httpStatus;
    private final String message;
}
