package com.cona.KUsukKusuk.email.dto;

public record EmailSendSuccessResponse(String message) {
    public static EmailSendSuccessResponse of(String message) {
        return new EmailSendSuccessResponse(message+"로 인증코드가 성공적으로 전송되었습니다. 유효기간은 30분 입니다.");
    }
}
