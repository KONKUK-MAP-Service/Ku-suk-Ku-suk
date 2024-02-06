package com.cona.KUsukKusuk.user.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    User user;

    @Test
    @DisplayName("유저 DTO를받아서 DB에저장한다.")
    void save() {
        UserJoinRequest userJoinRequest
                = new UserJoinRequest("donghun", "vdongv1620", "email", "최동훈");

        user = userJoinRequest.toEntity();
        BDDMockito.given(userRepository.save(any(User.class)))
                .willReturn(user);

        User savedUser = userService.save(userJoinRequest);

        Assertions.assertThat(savedUser).isEqualTo(user);

    }
}