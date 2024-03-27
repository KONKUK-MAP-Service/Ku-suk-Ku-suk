package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotUpdatResponse;
import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record CommentUpdateResponse (
        Long commentId, String nickname, Long spotId, String comment

){
    public static CommentUpdateResponse of(Comment comment){
        return CommentUpdateResponse.builder()
                .commentId(comment.getId())
                .nickname(comment.getUser().getNickname())
                .spotId(comment.getSpot().getId())
                .comment(comment.getComment())
                .build();
    }
}