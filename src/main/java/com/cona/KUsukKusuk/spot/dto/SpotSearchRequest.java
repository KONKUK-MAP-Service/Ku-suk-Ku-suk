package com.cona.KUsukKusuk.spot.dto;

import lombok.Builder;

@Builder
public record SpotSearchRequest(
        String searchText
) {
}
