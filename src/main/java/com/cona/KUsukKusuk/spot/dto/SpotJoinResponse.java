package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import lombok.Builder;

@Builder
public record SpotJoinResponse(
        String spotName, String longtitude, String latitude, String review
) {
    public static SpotJoinResponse of(Spot spot){
        return SpotJoinResponse.builder()
                //.id(spot.getUser())
                .spotName(spot.getSpotName())
                .longtitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .review(spot.getReview())
                .build();
    }
}
