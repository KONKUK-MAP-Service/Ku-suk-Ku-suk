package com.cona.KUsukKusuk.global.response;

public record ApiResponse<T>(int staus, T results) {
}
