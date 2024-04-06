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

    INCORRECT_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 리프레시 토큰입니다. 기한이 만료되었거나, 이미 로그아웃이 완료되어 DB에 존재하지 않는 상태입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 토큰을 재발급하세요"),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "토큰의 정보가 임의로 변경되었습니다."),
    UNKNOWN_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 존재하지 않습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰의 길이나 형식이 올바르지 않습니다."),
    HEADER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Authorization 헤더 정보가 존재하지 않습니다."),
    BEARER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "Bearer 로 Authorization 헤더가 시작되지 않습니다."),

    PASSWORD_NOT_MATCH(HttpStatus.CONFLICT, "현재 로그인한 사용자의 비밀번호와 맞지 않습니다."),
    USERID_EXIST(HttpStatus.CONFLICT,"이미 존재하는 아이디 입니다."),
    NICKNAME_ALREADY_EXIST(HttpStatus.CONFLICT,"이미 존재하는 닉네임 입니다."),
    USER_EXIST(HttpStatus.CONFLICT,"이미 존재하는 사용자 입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT,"이미 존재하는 이메일 입니다."),

    EMAIL_NOT_SEND(HttpStatus.NOT_FOUND,"이메일이 전송에 실패하였습니다."),
    IMAGE_UPLOAD_FAILED(HttpStatus.NOT_FOUND, "이미지 업로드에 실패하였습니다."),
    SPOT_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 spot ID가 DB에 존재하지 않습니다."),
    USER_LOGIN_PERMIT_FAIL(HttpStatus.FORBIDDEN,"로그인한 사용자만 이용할 수 있습니다. "),
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "해당 사용자가 작성한 장소가 아닙니다."),
    BOOK_MARK_ERR(HttpStatus.BAD_REQUEST, "북마크 조회에 실패하였습니다."),
    BOOK_MARK_NOT_EXIST(HttpStatus.NOT_FOUND, "등록된 북마크가 존재하지 않습니다."),
    LIKE_ERR(HttpStatus.BAD_REQUEST, "좋아요 조회에 실피하였습니다."),
    LIKE_NOT_EXIST(HttpStatus.NOT_FOUND, "등록된 좋아요가 존재하지 않습니다.");






    private final HttpStatus httpStatus;
    private final String message;
}
