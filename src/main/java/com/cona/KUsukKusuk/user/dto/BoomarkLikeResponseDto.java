package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotDetailResponse;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BoomarkLikeResponseDto(

        String spotName,
        String review,
        LocalDateTime createDate,

        String author,

        Boolean bookmark,
        Boolean like,
        String spotImageurl
) {
    public static BoomarkLikeResponseDto of(Spot spot, Boolean isBookmark, Boolean isLike) {
        return BoomarkLikeResponseDto.builder()
                .spotName(spot.getSpotName())
                .spotImageurl(spot.getImageUrls().get(0))
                .review(spot.getReview())
                .author(spot.getUser().getNickname())
                .bookmark(isBookmark)
                .like(isLike)
                .build();
    }
}


