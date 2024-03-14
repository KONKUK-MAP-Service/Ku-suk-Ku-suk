package com.cona.KUsukKusuk.profile.service;


import com.cona.KUsukKusuk.global.s3.S3Service;
import com.cona.KUsukKusuk.profile.dto.UploadImage;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final S3Service s3Service;
    private final UserService userService;
    private final UserRepository userRepository;


    public String uploadProfileImage(UploadImage imageDto) throws IOException {
        MultipartFile profileImage = imageDto.profileImage();

        String userId = userService.getUsernameBySecurityContext();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);


        String s3url = s3Service.saveProfileImage(userId, profileImage);

        user.setProfileimage(s3url);
        userRepository.save(user);

        return s3url;
    }


    @Transactional
    public void deleteProfileImage() {
        String userId = userService.getUsernameBySecurityContext();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getProfileimage() != null) {
            s3Service.deleteProfileImage(user);
            user.setProfileimage(null);
            userRepository.save(user);
        }
    }

    public String updateProfileImage(UploadImage imageDto) throws IOException {
        MultipartFile profileImage = imageDto.profileImage();

        String userId = userService.getUsernameBySecurityContext();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        s3Service.deleteProfileImage(user);

        String s3url = s3Service.saveProfileImage(userId, profileImage);

        user.setProfileimage(s3url);
        userRepository.save(user);

        return s3url;
    }


}
