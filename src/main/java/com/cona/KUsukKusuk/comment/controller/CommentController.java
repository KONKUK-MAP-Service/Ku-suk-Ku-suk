package com.cona.KUsukKusuk.comment.controller;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.dto.CommentJoinRequest;
import com.cona.KUsukKusuk.comment.dto.CommentJoinResponse;
import com.cona.KUsukKusuk.comment.service.CommentService;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("comment")
public class CommentController {
    final private CommentService commentService;
    private CommentJoinRequest CommentJoinRequest;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{spotId}/register")
    @Operation(summary = "특정 장소 글에 댓글 등록", description = "로그인한 사용자의 댓글을 등록합니다.")
    public HttpResponse<CommentJoinResponse> saveComment(@PathVariable("spotId") Long spotId, @RequestBody CommentJoinRequest commentJoinRequest){
        //user, spot 가져오기 - 수정 필요
        User user = commentService.getCurrentUser();
        Spot spot = commentService.getCurrentSpot(spotId);
        //위 user, spot 사용해서 comment 객체 만들기
        Comment comment = commentJoinRequest.toEntity(user, spot);
        Comment savedComment = commentService.save(comment);
        return  HttpResponse.okBuild(
                CommentJoinResponse.of(savedComment));
    }


}