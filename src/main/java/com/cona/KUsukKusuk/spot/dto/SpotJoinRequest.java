package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SpotJoinRequest (
    @NotNull(message = "장소명은 필수 입력값입니다.")
    @Schema(description = "장소명", nullable = false, example = "")
    String spotName,
    @NotNull(message = "경도 값은 필수 입력값입니다.")
    @Schema(description = "경도 값", nullable = false, example = "")
    String longitude,
    @NotNull(message = "위도 값은 필수 입력값입니다.")
    @Schema(description = "위도 값", nullable = false, example = "")
    String latitude,
    @NotNull(message = "리뷰는 필수 입력값입니다.")
    @Schema(description = "리뷰", nullable = false, example = "")
    String review
)
{
    public Spot toEntity() {
        return Spot.builder()
                .spotName(spotName)
                .longitude(longitude)
                .latitude(latitude)
                .review(review)
                .build();
    }
}
