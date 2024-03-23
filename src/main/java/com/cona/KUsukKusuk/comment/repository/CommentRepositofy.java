package com.cona.KUsukKusuk.comment.repository;

import com.cona.KUsukKusuk.comment.Comment;
import com.cona.KUsukKusuk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositofy extends JpaRepository<Comment, Long> {
    void deleteAllByUser(User user);

}
