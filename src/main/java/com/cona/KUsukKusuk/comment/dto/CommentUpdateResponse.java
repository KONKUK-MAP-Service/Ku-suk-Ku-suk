package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotUpdatResponse;
import com.cona.KUsukKusuk.user.domain.User;
import lombok.Builder;

@Builder
public record CommentUpdateResponse (
        Long id, User user, Spot spot, String comment

){
    public static CommentUpdateResponse of(Comment comment){
        return CommentUpdateResponse.builder()
                .id(comment.getId())
                .user(comment.getUser())
                .spot(comment.getSpot())
                .comment(comment.getComment())
                .build();
    }
}