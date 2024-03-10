package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.picture.Picture;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record SpotJoinResponse(
        Long id, String spotName, String longtitude, String latitude, String review, List<Picture> pictures,List<String> pictureUrls
) {
    public static SpotJoinResponse of(Spot spot){

        return SpotJoinResponse.builder()
                .id(spot.getId())
                .spotName(spot.getSpotName())
                .longtitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .pictures(spot.getPictures())
                .pictureUrls(spot.getPictureUrls())
                .review(spot.getReview())
                .build();
    }
}
