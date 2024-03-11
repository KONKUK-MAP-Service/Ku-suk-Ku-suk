package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserJoinRequest(

        @NotNull(message = "사용자 아이디는 필수 입력값입니다.")
        @Schema(description = "사용자 아이디", nullable = false, example = "konkuk")
        String userId,
        @NotNull(message = "사용자 비밀번호는 필수 입력값입니다.")
        @Schema(description = "사용자 비밀번호", nullable = false, example = "vdongv1620")
        String password,
        @NotNull(message = "사용자 이메일은 필수 입력값입니다.")
        @Schema(description = "사용자 이메일", nullable = false, example = "donghoon141@naver.com")
        String email,
        @NotNull(message = "사용자 닉네임은 필수 입력값입니다.")
        @Schema(description = "사용자 아이디", nullable = false, example = "최동훈")
        String nickname
)
{
        public User toEntity() {
                return User.builder()
                        .userId(userId)
                        .password(password)
                        .email(email)
                        .nickname(nickname)
                        .noCryptpassword(password)
                        .build();
        }

}
