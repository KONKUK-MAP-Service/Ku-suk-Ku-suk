package com.cona.KUsukKusuk.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FindPasswordRequest(
        @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
         String userId,

        @NotBlank(message = "이메일 값은 필수 입력 값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
         String email
) {

}
