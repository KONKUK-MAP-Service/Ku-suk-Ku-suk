package com.cona.KUsukKusuk.spot.repository;

import com.cona.KUsukKusuk.spot.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    Spot save(Spot spot);
}
