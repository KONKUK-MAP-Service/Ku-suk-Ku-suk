package com.cona.KUsukKusuk.user.repository;

import com.cona.KUsukKusuk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
