package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.spot.domain.Spot;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record SpotGetResponse(
        Long spotId,
        @Schema(description = "로그인한 사용자가 등록한 장소이면 True를 반환합니다.", nullable = false, example = "true")
         Boolean isUsersOwnSpot,
         String spotName,
        List<String>images,
       String longtitude,
        String latitude,
         String review,
        LocalDateTime createDate
) {
    public static SpotGetResponse of(Spot spot,Boolean bool) {
        return SpotGetResponse.builder()
                .spotId(spot.getId())
                .isUsersOwnSpot(bool)
                .spotName(spot.getSpotName())
                .images(spot.getImageUrls())
                .longtitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .review(spot.getReview())
                .createDate(spot.getCreatedDate())
                .build();
    }
}
