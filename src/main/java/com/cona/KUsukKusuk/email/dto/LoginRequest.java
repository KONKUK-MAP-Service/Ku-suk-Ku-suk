package com.cona.KUsukKusuk.email.dto;

import lombok.Builder;

@Builder
public record LoginRequest( String username, String password) {
}
