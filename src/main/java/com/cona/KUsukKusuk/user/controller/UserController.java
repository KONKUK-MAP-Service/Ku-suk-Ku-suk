package com.cona.KUsukKusuk.user.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.dto.UserJoinResponse;
import com.cona.KUsukKusuk.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public HttpResponse<UserJoinResponse> join(@Valid @RequestBody UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();
        User savedUser = userService.save(user);
        return HttpResponse.okBuild(
                UserJoinResponse.of(savedUser)
        );
    }
}
