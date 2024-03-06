package com.cona.KUsukKusuk.user.dto;

public record TokenRefreshResponse(String accessToken) {
    public static TokenRefreshResponse of(String accessToken) {
        return new TokenRefreshResponse(accessToken);
    }
}
