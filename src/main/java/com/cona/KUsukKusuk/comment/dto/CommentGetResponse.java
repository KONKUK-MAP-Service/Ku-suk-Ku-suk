package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentGetResponse (
        Long commentNum , Long spotId, Long commentId, String comment, LocalDateTime createDate, Long userId

){
    public static CommentGetResponse of(Long commentNum, Comment comment,LocalDateTime createDate)
    {
        return CommentGetResponse.builder()
                .commentNum(commentNum)
                .spotId(comment.getSpot().getId())
                .commentId(comment.getId())
                .comment(comment.getComment())
                .userId(comment.getUser().getId())
                .createDate(createDate)
                .build();
    }
}
