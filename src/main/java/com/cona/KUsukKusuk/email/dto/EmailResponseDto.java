package com.cona.KUsukKusuk.email.dto;

public record EmailResponseDto(boolean success) {
    public boolean isSuccess() {
        return success;
    }
    public static EmailResponseDto of(boolean success) {
        return new EmailResponseDto(success);
    }
}
