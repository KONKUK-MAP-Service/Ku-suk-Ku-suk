package com.cona.KUsukKusuk.user.service;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.exception.custom.security.SecurityJwtNotFoundException;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public void logout(String encryptedRefreshToken, String accessToken) {
        this.verifiedRefreshToken(encryptedRefreshToken);

    }

    private void verifiedRefreshToken(String encryptedRefreshToken) {
        if (encryptedRefreshToken == null) {
            throw new SecurityJwtNotFoundException(HttpExceptionCode.JWT_NOT_FOUND);
        }
    }



}
