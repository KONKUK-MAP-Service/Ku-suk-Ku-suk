package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record UserProfileResponse(
         String userId,
         String password,
         String nickname,
         String email,
         String profileImage
) {
    public static UserProfileResponse of(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .password(user.getNoCryptpassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileimage())
                .build();
    }
}
