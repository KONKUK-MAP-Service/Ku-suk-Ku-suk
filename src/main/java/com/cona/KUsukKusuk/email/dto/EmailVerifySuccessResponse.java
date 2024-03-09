package com.cona.KUsukKusuk.email.dto;

public record EmailVerifySuccessResponse(String message) {

    public static EmailVerifySuccessResponse of(Boolean success) {
        if (success) {
            return new EmailVerifySuccessResponse("이메일 인증이 성공적으로 완료되었습니다.");
        }
        else {
            return new EmailVerifySuccessResponse("인증코드가 올바르지 않습니다.");
        }
    }
}
