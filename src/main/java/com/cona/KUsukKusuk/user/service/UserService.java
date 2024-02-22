package com.cona.KUsukKusuk.user.service;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.exception.custom.security.RefreshTokenNotFoundException;
import com.cona.KUsukKusuk.global.exception.custom.security.SecurityJwtNotFoundException;
import com.cona.KUsukKusuk.global.redis.RedisService;
import com.cona.KUsukKusuk.global.security.JWTUtil;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisService redisService;
    private final JWTUtil jwtUtil;

    public User save(UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public String logout(String encryptedRefreshToken, String accessToken) {
        this.verifiedRefreshToken(encryptedRefreshToken);

        String result = addToBlacklist(encryptedRefreshToken);
        return result;

    }

    private void verifiedRefreshToken(String encryptedRefreshToken) {
        if (encryptedRefreshToken == null) {
            throw new SecurityJwtNotFoundException(HttpExceptionCode.JWT_NOT_FOUND);
        }
    }

    private String addToBlacklist(String encryptedRefreshToken) {
        String blacklistKey = encryptedRefreshToken;

        redisService.setValues(blacklistKey,"blacklist");
        return "blaklist "+blacklistKey;
    }
    public String refreshToken(String encryptedRefreshToken) {

        String userId = redisService.getValues(encryptedRefreshToken);
        if (userId == null) {
            throw new RefreshTokenNotFoundException();
        }
        User byUserId = userRepository.findByUserId(userId);
        if (byUserId == null) {
            throw new
        }

        // Validate and decode the refresh token
        Claims claims = jwtUtil.getAllClaimsFromToken(encryptedRefreshToken);

        // Extract user details from the refresh token
        String username = claims.getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SecurityJwtNotFoundException(HttpExceptionCode.USER_NOT_FOUND));

        // Generate a new access token
        String newAccessToken = jwtUtil.generateAccessToken(user);

        return newAccessToken;
    }


}
