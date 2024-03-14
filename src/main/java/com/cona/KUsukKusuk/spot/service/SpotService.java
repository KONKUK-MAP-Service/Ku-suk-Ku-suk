package com.cona.KUsukKusuk.spot.service;

import com.cona.KUsukKusuk.global.s3.S3Service;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.SpotGetResponse;
import com.cona.KUsukKusuk.spot.dto.SpotJoinResponse;
import com.cona.KUsukKusuk.spot.dto.SpotUploadRequest;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
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


}
