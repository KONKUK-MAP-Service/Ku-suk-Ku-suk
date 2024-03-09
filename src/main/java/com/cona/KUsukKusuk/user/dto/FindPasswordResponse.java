package com.cona.KUsukKusuk.user.dto;

import lombok.Builder;

@Builder
public record FindPasswordResponse(String message) {

    public static FindPasswordResponse of(String email) {


        return FindPasswordResponse.builder()
                .message(email+"로 임시 비밀번호를 보냈습니다.")
                .build();


    }
}
