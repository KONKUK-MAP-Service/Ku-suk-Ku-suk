package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.spot.dto.SpotDeleteResponse;
import lombok.Builder;

@Builder
public record CommentDeleteResponse (
        String message
){
    public static CommentDeleteResponse of(String message) {
        return CommentDeleteResponse.builder()
                .message(message)
                .build();

    }
}
