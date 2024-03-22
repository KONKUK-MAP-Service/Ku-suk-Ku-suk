package com.cona.KUsukKusuk.bookmark.dto;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.spot.domain.Spot;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BookmarkResponseDto(
        Long bookmarkId,
        String spotname,
        String review,
        LocalDateTime createDate,
        String SpotImageurl

) {
    public static BookmarkResponseDto of(Bookmark bookmark, Spot spot) {

        return BookmarkResponseDto.builder()
                .bookmarkId(bookmark.getId())
                .spotname(spot.getSpotName())
                .review(spot.getReview())
                .createDate(bookmark.createdDate)
                .build();
    }
}
