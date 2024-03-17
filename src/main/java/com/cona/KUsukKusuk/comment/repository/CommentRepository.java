package com.cona.KUsukKusuk.comment.repository;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByUser(User user);

}
