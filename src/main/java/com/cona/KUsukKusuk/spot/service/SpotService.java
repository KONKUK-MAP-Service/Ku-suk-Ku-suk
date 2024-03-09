package com.cona.KUsukKusuk.spot.service;

import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class SpotService {
    final private SpotRepository spotRepository;

    public SpotService(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public Spot save(Spot spot) {
//        spot.setUserLikes();
//        spot.setPictures();
//        spot.setComments();
//        spot.setBookmarks();
        Spot savedSpot = spotRepository.save(spot);
        return savedSpot;
    }

//    public User findbyId(Long id) {
//
//    }
}
