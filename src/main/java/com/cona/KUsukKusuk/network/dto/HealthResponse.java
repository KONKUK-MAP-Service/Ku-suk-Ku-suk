package com.cona.KUsukKusuk.network.dto;

import lombok.Builder;

@Builder
public record HealthResponse(String message) {
    public static HealthResponse from(
            String message) {

        return HealthResponse.builder()
                .message(message)
                .build();
    }

}
