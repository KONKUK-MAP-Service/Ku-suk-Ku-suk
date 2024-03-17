package com.cona.KUsukKusuk.comment.service;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.repository.CommentRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    final private CommentRepository commentRepository;
    private final UserService userService;
    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Comment save(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    public User getCurrentUser() {
        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(userId);
        return user;
    }

    public Spot getCurrentSpot() {//수정할 예정
    }
}

