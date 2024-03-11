package com.cona.KUsukKusuk.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record CheckPasswordRequest(
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Schema(description = "사용자 비밀번호", nullable = false, example = "vdongv1620")

         String password
) {

}
