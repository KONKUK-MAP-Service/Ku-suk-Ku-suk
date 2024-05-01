package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.global.s3.ImageUrlConverter;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record SpotJoinResponse(
        Long spotid,String spotName, List<String> images, String longtitude, String latitude, String review
) {
    public static SpotJoinResponse of(Spot spot){
        List<String> cloudFrontImageUrls = ImageUrlConverter.convertToCloudFrontUrls(spot.getImageUrls());

        return SpotJoinResponse.builder()
                .spotid(spot.getId())
                .spotName(spot.getSpotName())
                .images(cloudFrontImageUrls)
                .longtitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .review(spot.getReview())
                .build();
    }
}
