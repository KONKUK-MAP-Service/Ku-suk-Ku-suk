package com.cona.KUsukKusuk.spot.dto;

import lombok.Builder;

@Builder
public record SpotDeleteResponse(
        String message
) {
    public static SpotDeleteResponse of(String message) {
        return SpotDeleteResponse.builder()
                .message(message)
                .build();

    }
}
