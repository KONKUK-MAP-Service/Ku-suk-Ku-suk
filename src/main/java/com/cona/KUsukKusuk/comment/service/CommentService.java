package com.cona.KUsukKusuk.comment.service;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.dto.CommentGetResponse;
import com.cona.KUsukKusuk.comment.dto.CommentPaginationResponse;
import com.cona.KUsukKusuk.comment.exception.CommentNotFoundException;
import com.cona.KUsukKusuk.comment.exception.CommentUserNotMatchedException;
import com.cona.KUsukKusuk.comment.repository.CommentRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotGetResponse;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final SpotRepository spotRepository;
    private final UserService userService;
    public CommentService(CommentRepository commentRepository, SpotRepository spotRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.spotRepository = spotRepository;
        this.userService = userService;
    }
    public Comment save(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    public User getCurrentUser() {
        String name = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(name);
        return user;
    }

    public  Spot getCurrentSpot(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());
        return spot;
    }

    public Comment getCurrentComment(String commentUserName , Spot spot, Long commentId) throws CommentNotFoundException, CommentUserNotMatchedException {
        List<Comment> commentList = spot.getComments();
        Comment wantToUpdate = null; // 초기화를 null로 설정
        for (Comment comment : commentList) {
            if (comment.getId().equals(commentId)) { // spot 내의 comment인지 확인
                wantToUpdate = comment;
                break; // 원하는 comment를 찾았으므로 루프 종료
            }
        }

        // wantToUpdate가 여전히 null인 경우 예외 처리
        if (wantToUpdate == null) {
            throw new CommentNotFoundException("Comment with id: " + commentId + " not found.");
        }

        //commentUserName과 comment의 작성자 일치 확인
        if (wantToUpdate.getUser().getUserId().equals(commentUserName))
            return wantToUpdate;
        else
            throw new CommentUserNotMatchedException("Don't have authority to update the comment.");

    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }


    public List<CommentGetResponse> getUserCommentsOfAllSpots(Long userId) {
        //목표 : 사용자가 쓴 comment만 list<CommentGetResponse> 형태로 반환
        List<Comment> comments = commentRepository.findAll();
        List<CommentGetResponse> commentsByuser = new ArrayList<>();
        Long cNum = 0L;
        for (Comment c : comments)
        {
            if (c.getUser().getId().equals(userId))
                commentsByuser.add(CommentGetResponse.of(++cNum,c,c.getCreatedDate()));
        }

        return commentsByuser;

    }

    public List<CommentPaginationResponse> getPagedComments(List<CommentGetResponse> commentsByUser) {
        //commentsByuser 한 객체마다 pagination 해줘서 pagedComments 에 넣어주기
        List<CommentPaginationResponse> pagedComments = new ArrayList<>();

        Long totalComments = (long) commentsByUser.size();
        Long amountInBlock = 10L;
        Long lastPage = totalComments / amountInBlock; // 전체 페이지 수
        // 나머지가 0보다 큰 경우에는 몫에 1을 더해주기
        if (totalComments % amountInBlock > 0) {
            lastPage++;
        }

        Long curPageNum = 1L;

        for (int i = 0; i < totalComments; i += amountInBlock) {
            int endIndex = (int) Math.min(i + amountInBlock, commentsByUser.size());
            List<CommentGetResponse> currentPageComments = commentsByUser.subList(i, endIndex);
            pagedComments.add(CommentPaginationResponse.of(currentPageComments, totalComments, curPageNum, lastPage, (long) (endIndex-i)));
            curPageNum++;
        }
        return pagedComments;
    }
}