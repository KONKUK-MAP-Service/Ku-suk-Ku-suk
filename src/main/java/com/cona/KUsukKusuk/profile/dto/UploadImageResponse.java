package com.cona.KUsukKusuk.profile.dto;

import lombok.Builder;

@Builder
public record UploadImageResponse(
        String message,
        String s3url
) {
    public static UploadImageResponse of(String s3url) {
        return UploadImageResponse.builder()
                .message("프로필 이미지 등록에 성공하였습니다.")
                .s3url(s3url)
                .build();

    }
}
