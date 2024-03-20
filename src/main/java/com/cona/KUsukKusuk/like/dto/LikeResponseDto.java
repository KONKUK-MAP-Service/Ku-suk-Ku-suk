package com.cona.KUsukKusuk.like.dto;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.bookmark.dto.BookmarkResponseDto;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.spot.domain.Spot;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record LikeResponseDto(
        Long likeId,
        String spotname,
        String review,
        LocalDateTime createDate,
        String SpotImageurl
) {
    public static LikeResponseDto of(UserLike userLike, Spot spot) {

        return LikeResponseDto.builder()
                .likeId(userLike.getId())
                .spotname(spot.getSpotName())
                .review(spot.getReview())
                .createDate(userLike.createdDate)
                .build();
    }
}
