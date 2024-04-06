package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.dto.BoomarkLikeResponseDto;
import com.cona.KUsukKusuk.user.dto.PageInfo;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentListResponseDto(
        String spotName,
        Long spotId,
        Long commentId,

        String usersComment,
        String review,
        LocalDateTime CommentcreateDate,

        String author,


        String spotImageurl,
        long totalElements,
        int page,
        int size,
        int totalPages
) {

    public static CommentListResponseDto of (Comment comment, PageInfo pageInfo){
        return CommentListResponseDto.builder()
                .spotName(comment.getSpot().getSpotName())
                .spotImageurl(comment.getSpot().getImageUrls().get(0))
                .commentId(comment.getId())
                .CommentcreateDate(comment.getCreatedDate())
                .spotId(comment.getId())
                .review(comment.getSpot().getReview())
                .author(comment.getUser().getNickname())
                .usersComment(comment.getComment())
                .totalElements(pageInfo.getTotalElements())
                .page(pageInfo.getPage())
                .size(pageInfo.getSize())
                .totalPages(pageInfo.getTotalPages())
                .build();
    }


}
