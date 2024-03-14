package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.spot.domain.Spot;
import java.util.List;
import lombok.Builder;

@Builder
public record SpotGetResponse(
        Long spotId,
         Boolean isUsersOwnSpot,
         String spotName,
        List<String>images,
       String longtitude,
        String latitude,
         String review
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
                .build();
    }
}
