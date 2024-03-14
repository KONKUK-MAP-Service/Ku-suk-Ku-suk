package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import java.util.List;
import lombok.Builder;

@Builder
public record SpotDetailResponse(Long spotId,
                                 Boolean isUsersOwnSpot,
                                 String spotName,
                                 List<String> images,
                                 String longitude,
                                 String latitude,
                                 String review) {

    public static SpotDetailResponse fromSpot(Spot spot, boolean isUsersOwnSpot) {
        return SpotDetailResponse.builder()
                .spotId(spot.getId())
                .isUsersOwnSpot(isUsersOwnSpot)
                .spotName(spot.getSpotName())
                .images(spot.getImageUrls())
                .longitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .review(spot.getReview())
                .build();
    }


}
