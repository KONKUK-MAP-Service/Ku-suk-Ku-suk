package com.cona.KUsukKusuk.profile.controller;


import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.global.s3.ImageUrlConverter;
import com.cona.KUsukKusuk.profile.dto.UploadImage;
import com.cona.KUsukKusuk.profile.exception.ImageUploadException;
import com.cona.KUsukKusuk.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile-image")
@Tag(name = "프로필 사진 컨트롤러", description = "프로필 이미지 업로드/삭제 컨트롤러 입니다.")

public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @DeleteMapping("/delete")
    @Operation(summary = "프로필 이미지 삭제", description = "프로필 이미지를 삭제합니다.")
    public HttpResponse<String> deleteProfileImage() {
        profileService.deleteProfileImage();
        return HttpResponse.okBuild("프로필 이미지 삭제가 성공하였습니다.");
    }
    @PutMapping("/update")
    @Operation(summary = "프로필 이미지 업로드", description = "로그인한 사용자의 프로필 이미지를 업로드합니다. ")
    public HttpResponse<String> updateProfileImage(UploadImage imageDto) {
        try {
            String imageUrl = profileService.updateProfileImage(imageDto);
            return HttpResponse.okBuild(imageUrl);
        } catch (IOException e) {
            throw new ImageUploadException();
        }
    }
}
