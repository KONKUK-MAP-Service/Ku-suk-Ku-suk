package com.cona.KUsukKusuk.like.service;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.like.dto.LikeRequest;
import com.cona.KUsukKusuk.like.repository.UserLikeRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import com.cona.KUsukKusuk.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserLikeRepository userLikeRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final UserService userService;

    public void addLike(LikeRequest likeDto) {
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);

        Spot spot = spotRepository.findById(likeDto.spotId())
                .orElseThrow(() -> new SpotNotFoundException(HttpExceptionCode.SPOT_NOT_FOUND));

        UserLike userLike = new UserLike();
        userLike.setUser(user);
        userLike.setSpot(spot);
        userLikeRepository.save(userLike);
    }


}
