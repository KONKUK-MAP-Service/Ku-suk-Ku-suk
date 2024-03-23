package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record CommentJoinResponse (
        Long id, User user, Spot spot, String comment
)
{
    public static CommentJoinResponse of(Comment comment){
        return CommentJoinResponse.builder()
                .id(comment.getId())
                .user(comment.getUser())
                .spot(comment.getSpot())
                .comment(comment.getComment())
                .build();
    }
}