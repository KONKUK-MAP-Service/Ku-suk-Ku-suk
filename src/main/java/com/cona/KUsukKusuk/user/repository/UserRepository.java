package com.cona.KUsukKusuk.user.repository;

import com.cona.KUsukKusuk.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userid);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Optional<User> findByUserId(String userid);

    Optional<User> findByUserIdAndEmail(String userId, String email);

}
