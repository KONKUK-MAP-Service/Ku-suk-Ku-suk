package com.cona.KUsukKusuk.like.service;

import com.cona.KUsukKusuk.bookmark.exception.BookmarkException;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.like.dto.LikeRequest;
import com.cona.KUsukKusuk.like.dto.LikeResponseDto;
import com.cona.KUsukKusuk.like.dto.UnlikeRequest;
import com.cona.KUsukKusuk.like.exception.LikeException;
import com.cona.KUsukKusuk.like.repository.UserLikeRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import com.cona.KUsukKusuk.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
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
    public void removeLike(UnlikeRequest unlikeDto) {
        Long spotId = unlikeDto.spotId();

        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException(HttpExceptionCode.SPOT_NOT_FOUND));

        UserLike userLike = userLikeRepository.findByUserAndSpot(user, spot)
                .orElseThrow(() -> new LikeException(HttpExceptionCode.LIKE_NOT_EXIST));


        userLikeRepository.delete(userLike);

    }
    public List<LikeResponseDto> getUserLikes() {
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);

        List<UserLike> userLikes = userLikeRepository.findByUser(user);

        if (userLikes == null || userLikes.isEmpty()) {
            throw new LikeException(HttpExceptionCode.LIKE_NOT_EXIST);
        }

        return userLikes.stream()
                .map(userLike -> LikeResponseDto.of(userLike,userLike.getSpot()))
                .collect(Collectors.toList());
    }


}
