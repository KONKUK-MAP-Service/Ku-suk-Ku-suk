package com.cona.KUsukKusuk.comment.dto;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class CommentJoinRequest {

    @NotNull(message = "댓글은 필수 입력값입니다.")
    @Schema(description = "댓글", nullable = false, example = "")
    String comments;

    // 생성자, getter 등 추가

    public Comment toEntity(User u, Optional<Spot> s) {
        return Comment.builder()
                .user(u)
                .spot(s)
                .comment(comments)
                .build();
    }

}