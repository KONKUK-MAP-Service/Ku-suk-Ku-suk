package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import java.util.List;
import lombok.Builder;

@Builder
public record SpotUpdatResponse(
        Long spotid, String spotName, List<String> images, String longtitude, String latitude, String review

) {
    public static SpotUpdatResponse of(Spot spot){
        return SpotUpdatResponse.builder()
                .spotid(spot.getId())
                .spotName(spot.getSpotName())
                .images(spot.getImageUrls())
                .longtitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .review(spot.getReview())
                .build();
    }
}
