package com.cona.KUsukKusuk.spot.service;

import com.cona.KUsukKusuk.picture.Picture;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotService {
    final private SpotRepository spotRepository;

    public SpotService(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public Spot save(Spot spot) {
        Spot savedSpot = spotRepository.save(spot);
        return savedSpot;
    }

    public List<Picture> changeStringToPicture(List<String> pictureUrls, Spot spot) {
        List<Picture> pictures = new ArrayList<>();
        for (String url : pictureUrls){
            pictures.add(new Picture(spot,url));
        }
        return pictures;
    }
}
