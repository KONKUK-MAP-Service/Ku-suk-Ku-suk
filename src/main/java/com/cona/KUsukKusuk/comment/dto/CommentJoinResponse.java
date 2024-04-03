package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record CommentJoinResponse (
        Long commentId, String nickname, Long spotId, String comment,String profileImage
)
{
    public static CommentJoinResponse of(Comment comment){
        return CommentJoinResponse.builder()
                .commentId(comment.getId())
                .nickname(comment.getUser().getNickname())
                .profileImage(comment.getUser().getProfileimage())
                .spotId(comment.getSpot().getId())
                .comment(comment.getComment())
                .build();
    }
}