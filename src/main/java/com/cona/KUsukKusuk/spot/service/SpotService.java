package com.cona.KUsukKusuk.spot.service;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.global.s3.S3Service;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotDetailResponse;
import com.cona.KUsukKusuk.spot.dto.SpotGetResponse;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.spot.dto.SpotUpdateRequest;
import com.cona.KUsukKusuk.spot.dto.SpotUploadRequest;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final S3Service s3Service;


    public Spot uploadSpot(SpotUploadRequest spotUploadRequest) throws IOException {

        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(userId);

        List<MultipartFile> images = spotUploadRequest.Images();
        List<String> imageUrls = s3Service.uploadImages(images, userId);

        Spot spot = spotUploadRequest.toEntity(imageUrls);
        spot.setUser(user);

        user.getSpots().add(spot);

        Spot savedSpot = spotRepository.save(spot);
        userRepository.save(user)
        return savedSpot;
    }
    public List<SpotGetResponse> getAllSpots() {
        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(userId);

        List<Spot> spots = spotRepository.findAll();
        return spots.stream()
                .map(spot -> SpotGetResponse.of(spot, spot.getUser().equals(user)))
                .collect(Collectors.toList());
    }
    public SpotDetailResponse getSpotDetails(Long spotId) {

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());

        boolean isUsersOwnSpot = spot.getUser().getId().equals(false);
        return SpotDetailResponse.fromSpot(spot);
    }
    public List<SpotGetResponse> getAllPublicSpots() {

        List<Spot> spots = spotRepository.findAll();
        return spots.stream()
                .map(spot -> SpotGetResponse.of(spot, false))
                .collect(Collectors.toList());
    }
    public void updateSpot(Long spotId, SpotUpdateRequest spotUpdateRequest) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException(HttpExceptionCode.USER_LOGIN_PERMIT_FAIL);
        }
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());
        if (!spot.getUser().equals(user)) {
            throw new UserNotFoundException(HttpExceptionCode.USER_NOT_MATCH);
        }

        s3Service.deleteSpotImages(spot,user);
        s3Service.uploadImages(spotUpdateRequest.Images(),username);
        spot.setSpotName(spotUpdateRequest.spotName());
        spot.setReview(spotUpdateRequest.review());


        spotRepository.save(spot);
    }







}
