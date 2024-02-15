package com.cona.KUsukKusuk.user.dto;

import lombok.Builder;

@Builder
public record UserLogoutResponse(String message,String blacklist) {

    public UserLogoutResponse from(String username,String blacklist){

        return UserLogoutResponse.builder()
                .message("요청하신" + username + "의 리프레시토큰이 블랙리스트 처리되었습니다.")
                .blacklist(blacklist)
                .build();
    }
}
