package com.cona.KUsukKusuk.service;

import com.cona.KUsukKusuk.spot.domain.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class SpotRepository {
    private final SqlSessionTemplate sql;
    @Transactional
    public Spot save(Spot spot) {
        sql.insert("Board.save", spot); //mapper의 namespace, 넘길 객체
        return spot;
    }
}
