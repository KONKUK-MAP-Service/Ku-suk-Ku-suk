package com.cona.KUsukKusuk.comment.controller;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.dto.*;
import com.cona.KUsukKusuk.comment.exception.CommentNotFoundException;
import com.cona.KUsukKusuk.comment.exception.CommentUserNotMatchedException;
import com.cona.KUsukKusuk.comment.service.CommentService;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.service.SpotService;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("comment")
public class CommentController {
    final private CommentService commentService;
    final private UserService userService;
    final private SpotService spotService;
    private CommentJoinRequest CommentJoinRequest;

    public CommentController(CommentService commentService, UserService userService, SpotService spotService) {
        this.commentService = commentService;
        this.userService = userService;
        this.spotService = spotService;
    }

    @PostMapping("/{spotId}/register")
    @Operation(summary = "특정 장소 글에 댓글 등록", description = "로그인한 사용자의 댓글을 등록합니다.")
    public HttpResponse<CommentJoinResponse> saveComment(@PathVariable("spotId") Long spotId, @RequestBody CommentJoinRequest commentJoinRequest){
        //user, spot 가져오기 - 수정 필요
        User user = commentService.getCurrentUser();
        Spot spot = commentService.getCurrentSpot(spotId);
        //위 user, spot 사용해서 comment 객체 만들기
        //System.out.println("user, spot 까지 담기 완료");
        Comment comment = commentJoinRequest.toEntity(user, spot);
        //System.out.println("comment 객체 생성 완료");
        Comment savedComment = commentService.save(comment);
        return  HttpResponse.okBuild(
                CommentJoinResponse.of(savedComment));
    }

    @PostMapping("/{spotId}/{commentId}/update")
    @Operation(summary = "특정 장소 글의 댓글 수정", description = "로그인한 사용자의 자신의 댓글을 수정합니다.")
    public HttpResponse<CommentUpdateResponse> updateComment(@PathVariable("spotId")Long spotId, @PathVariable("commentId")Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) throws CommentNotFoundException, CommentUserNotMatchedException {

        String commentUserName = userService.getUsernameBySecurityContext();


        Spot spot = commentService.getCurrentSpot(spotId);
        Comment updateComment = commentService.getCurrentComment(commentUserName,spot,commentId);
        updateComment.setComment(commentUpdateRequest.comment());

        Comment savedComment = commentService.save(updateComment);
        return HttpResponse.okBuild(
                CommentUpdateResponse.of(updateComment)
        );
    }

    @GetMapping("/{spotId}/{commentId}/delete")
    @Operation(summary = "특정 장소 글의 댓글 삭제" , description = "로그인한 사용자의 자신의 댓글을 삭제합니다.")
    public HttpResponse<CommentDeleteResponse> deleteComment(@PathVariable("spotId")Long spotId, @PathVariable("commentId")Long commentId) throws CommentNotFoundException, CommentUserNotMatchedException {

        String commentUserName = userService.getUsernameBySecurityContext();
        Spot spot = commentService.getCurrentSpot(spotId);
        Comment comment = commentService.getCurrentComment(commentUserName,spot,commentId);

        commentService.delete(comment);
        return HttpResponse.okBuild(
                CommentDeleteResponse.of("댓글이 삭제 되었습니다.")
        );
    }

    @GetMapping("/myAllComments")
    @Operation(summary = "자신의 댓글 전체 조회", description = "로그인한 사용자의 댓글을 전체 조회합니다.('페이지 위치:보여지는 댓글 수'형식으로 입력받습니다.)")
    public HttpResponse<List<CommentListResponseDto>> allComments(@RequestParam(defaultValue = "1") int pageNumber,
                                                               @RequestParam(defaultValue = "10") int pageSize){

        List<CommentListResponseDto> pagedComments = commentService.getPagedComments(pageNumber, pageSize);
        return  HttpResponse.okBuild(
                pagedComments);
    }


}