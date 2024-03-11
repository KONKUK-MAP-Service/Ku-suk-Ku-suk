package com.cona.KUsukKusuk.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LogoutRequest(
        @Schema(description = "리프레시 토큰을 보내면 AccessToken 재발급", nullable = false, example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOiJrb25rdWsiLCJwYXNzd29yZCI6IiQyYSQxMCQweno4bnl3aXZFN1ZnVURKeW5GOC9lV28yWGs2Q3hjZC9lTnBpNE4zT2VVTllvTUxqNHFULiIsImlhdCI6MTcxMDE3OTQyNCwiZXhwIjoxNzEwNzg0MjI0fQ.-3_E4vP0de7_6LW-5m2f5krG1ytHDH2gw30L0PX4NKU")
         String refreshtoken
) {
}
