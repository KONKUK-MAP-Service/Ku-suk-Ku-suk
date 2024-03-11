package com.cona.KUsukKusuk.user.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.global.security.JWTUtil;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.CheckPasswordRequest;
import com.cona.KUsukKusuk.user.dto.FindPasswordRequest;
import com.cona.KUsukKusuk.user.dto.FindPasswordResponse;
import com.cona.KUsukKusuk.user.dto.TokenRefreshRequest;
import com.cona.KUsukKusuk.user.dto.TokenRefreshResponse;
import com.cona.KUsukKusuk.user.dto.UpdateProfileResponse;
import com.cona.KUsukKusuk.user.dto.UpdateUserProfileRequest;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import com.cona.KUsukKusuk.user.dto.UserLogoutResponse;
import com.cona.KUsukKusuk.user.dto.UserProfileResponse;
import com.cona.KUsukKusuk.user.exception.PasswordNotMatchException;
import com.cona.KUsukKusuk.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")

    public HttpResponse<UserJoinResponse> join(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        User savedUser = userService.save(userJoinRequest);
        return HttpResponse.okBuild(
                UserJoinResponse.of(savedUser)
        );
    }
    @PatchMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃을 요청하여 RfreshToken을 블랙처리 합니다.")

    public HttpResponse<UserLogoutResponse> logout(HttpServletRequest request) {

        String username = userService.getUsernameBySecurityContext();
        String encryptedRefreshToken = jwtUtil.getRefreshToken(request);
        String blacklist = userService.logout(encryptedRefreshToken);

        return HttpResponse.okBuild(
                UserLogoutResponse.from(username,blacklist)
        );
    }
    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신", description = "만료된 AccessToken을 RefreshToken을 사용해 갱신합니다.")
    public HttpResponse<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest refreshRequest) {
        String newAccessToken = userService.refreshToken(refreshRequest.refreshToken());
        return HttpResponse.okBuild(
                TokenRefreshResponse.of(newAccessToken)
        );
    }
    @PostMapping("/find-password")
    @Operation(summary = "비밀번호 찾기", description = "아이디와 이메일로 임시 비밀번호를 발급합니다.")
    public HttpResponse<FindPasswordResponse> findPassword(@Valid @RequestBody FindPasswordRequest findPasswordRequest) {
        String email = userService.findPassword(findPasswordRequest.userId(), findPasswordRequest.email());
        return HttpResponse.okBuild(
                FindPasswordResponse.of(email)
        );
    }
    @PostMapping("/check-password")
    @Operation(summary = "사용자 비밀번호 확인", description = "현재 로그인 한 사용자의 비밀번호를 확인합니다.")
    public HttpResponse<String> checkPassword(@Valid @RequestBody CheckPasswordRequest checkPasswordRequest) {
        userService.checkPassword(checkPasswordRequest.getPassword());

        return HttpResponse.okBuild("비밀번호가 일치합니다.");

    }
    @GetMapping("/profile")
    @Operation(summary = "마이페이지 프로필 조회", description = "현재 로그인한 사용자의 프로필을 조회합니다.")
    public HttpResponse<UserProfileResponse> getUserProfile() {
        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findMemberByUsername(userId);
        return HttpResponse.okBuild(
                UserProfileResponse.of(user)
        );
    }

    @PatchMapping("/update-profile")
    @Operation(summary = "마이페이지 프로필 수정", description = "현재 로그인한 사용자의 프로필 정보를 수정합니다.")
    public HttpResponse<UpdateProfileResponse> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest request) {
        String userId = userService.getUsernameBySecurityContext();
        User updatedUser = userService.updateUserProfile(userId, request);
        return HttpResponse.okBuild(
                UpdateProfileResponse.of(updatedUser)
        );
    }

}
