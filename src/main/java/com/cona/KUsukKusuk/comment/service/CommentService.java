package com.cona.KUsukKusuk.comment.service;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.exception.CommentNotFoundException;
import com.cona.KUsukKusuk.comment.exception.CommentUserNotMatchedException;
import com.cona.KUsukKusuk.comment.repository.CommentRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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
        if (wantToUpdate.getUser().getNickname().equals(commentUserName))
            return wantToUpdate;
        else
            throw new CommentUserNotMatchedException("Don't have authority to update the comment.");

    }

}

