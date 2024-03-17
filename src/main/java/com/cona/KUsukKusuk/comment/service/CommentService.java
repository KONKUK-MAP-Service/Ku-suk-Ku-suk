package com.cona.KUsukKusuk.comment.service;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.repository.CommentRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import org.springframework.stereotype.Service;

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
        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(userId);
        return user;
    }

    public  Optional<Spot> getCurrentSpot(Long spotId) {//수정할 예정
        Optional<Spot> spot = spotRepository.findById(spotId);
        return spot;
    }
}

