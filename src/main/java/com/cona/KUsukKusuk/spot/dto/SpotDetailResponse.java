package com.cona.KUsukKusuk.spot.dto;

import com.cona.KUsukKusuk.global.s3.ImageUrlConverter;
import com.cona.KUsukKusuk.spot.domain.Spot;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record SpotDetailResponse(Long spotId,

                                 String spotName,
                                 List<String> images,
                                 String longitude,
                                 String latitude,
                                 String author,
                                 Boolean bookmark,
                                 Boolean like,
                                 LocalDateTime createDate,


                                 String review) {

    public static SpotDetailResponse fromSpot(Spot spot,Boolean isBookmark, Boolean isLike) {

        List<String> cloudFrontImageUrls = ImageUrlConverter.convertToCloudFrontUrls(spot.getImageUrls());

        return SpotDetailResponse.builder()
                .spotId(spot.getId())

                .spotName(spot.getSpotName())
                .images(cloudFrontImageUrls)
                .longitude(spot.getLongitude())
                .latitude(spot.getLatitude())
                .review(spot.getReview())
                .author(spot.getUser().getNickname())
                .bookmark(isBookmark)
                .createDate(spot.getCreatedDate())
                .like(isLike)
                .build();
    }


}
