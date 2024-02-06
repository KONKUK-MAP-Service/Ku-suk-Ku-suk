package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record UserJoinResponse(
        Long id, String userid,String password,String email,String nickname
) {
    public static UserJoinResponse of(User user){
        return UserJoinResponse.builder()
                .id(user.getId())
                .userid(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
