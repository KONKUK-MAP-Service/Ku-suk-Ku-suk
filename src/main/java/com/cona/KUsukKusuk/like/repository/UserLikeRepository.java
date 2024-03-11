package com.cona.KUsukKusuk.like.repository;

import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    void deleteAllByUser(User user);
}
