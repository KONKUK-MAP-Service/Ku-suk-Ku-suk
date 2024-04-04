package com.cona.KUsukKusuk.comment.repository;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByUser(User user);

    List<Comment> findByUser(User user);

}
