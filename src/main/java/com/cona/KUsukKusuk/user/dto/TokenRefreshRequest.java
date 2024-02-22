package com.cona.KUsukKusuk.user.dto;

import lombok.Getter;

@Getter
public record TokenRefreshRequest(String accessToken) {

}
