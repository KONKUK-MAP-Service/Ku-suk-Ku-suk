package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.global.s3.ImageUrlConverter;
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
        // S3 URL을 CloudFront URL로 변환
        String cloudFrontUrl = ImageUrlConverter.convertToCloudFrontUrl(user.getProfileimage());
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .password(user.getNoCryptpassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(cloudFrontUrl)
                .build();
    }
}
