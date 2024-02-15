package com.cona.KUsukKusuk.user.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.global.security.JWTUtil;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public HttpResponse<UserJoinResponse> join(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        User savedUser = userService.save(userJoinRequest);
        return HttpResponse.okBuild(
                UserJoinResponse.of(savedUser)
        );
    }
    @PatchMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String encryptedRefreshToken = jwtUtil.getRefreshToken(request);
        String accessToken = jwtUtil.getAccessToken(request);
        userService.logout(encryptedRefreshToken, accessToken);

        return new ResponseEntity<>(new SingleResponseDto<>("Logged out successfully"), HttpStatus.NO_CONTENT);
    }
}
