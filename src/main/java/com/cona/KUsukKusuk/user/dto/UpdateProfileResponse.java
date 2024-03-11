package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record UpdateProfileResponse(
        String message,
        String userId,
        String password,
        String nickname,
        String email,
        String profileImage
) {
    public static UpdateProfileResponse of(User user) {
        return UpdateProfileResponse.builder()
                .message("프로필이 성공적으로 수정되었습니다.")
                .userId(user.getUserId())
                .password(user.getNoCryptpassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileimage())
                .build();
    }
}
