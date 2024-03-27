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
        String spotImageurl,
        long totalElements,
        int page,
        int size,
        int totalPages
) {
    public static BoomarkLikeResponseDto of(Spot spot, Boolean isBookmark, Boolean isLike, PageInfo pageInfo) {
        return BoomarkLikeResponseDto.builder()
                .spotName(spot.getSpotName())
                .spotImageurl(spot.getImageUrls().get(0))
                .review(spot.getReview())
                .author(spot.getUser().getNickname())
                .bookmark(isBookmark)
                .like(isLike)
                .totalElements(pageInfo.getTotalElements())
                .page(pageInfo.getPage())
                .size(pageInfo.getSize())
                .totalPages(pageInfo.getTotalPages())
                .build();
    }
}


