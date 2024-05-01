package com.cona.KUsukKusuk.user.dto;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotDetailResponse;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BoomarkLikeResponseDto(

        String spotName,
        Long spotId,
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
        String spotImageurl = spot.getImageUrls().isEmpty() ? "" : spot.getImageUrls().get(0);
        return BoomarkLikeResponseDto.builder()
                .spotName(spot.getSpotName())
                .spotImageurl(spotImageurl)
                .createDate(spot.getCreatedDate())
                .spotId(spot.getId())
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


