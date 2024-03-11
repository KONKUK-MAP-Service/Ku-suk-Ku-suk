package com.cona.KUsukKusuk.spot.repository;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface SpotRepository extends JpaRepository<Spot, Long> {
    void deleteAllByUser(User user);
}
