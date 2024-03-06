package com.cona.KUsukKusuk.global.dto;

import lombok.Builder;

@Builder
public record LoginRequest(String username, String password) {
}
