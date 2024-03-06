package com.cona.KUsukKusuk.user.service;

import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.exception.custom.security.SecurityJwtNotFoundException;
import com.cona.KUsukKusuk.global.redis.RedisService;
import com.cona.KUsukKusuk.global.security.JWTUtil;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        this.isTokenPresent(encryptedRefreshToken);

        String result = addToBlacklist(encryptedRefreshToken);
        return result;

    }


    private String addToBlacklist(String encryptedRefreshToken) {
        String blacklistKey = encryptedRefreshToken;

        redisService.setValues(blacklistKey, "blacklist");
        return "blaklist " + blacklistKey;
    }

    public String refreshToken(String encryptedRefreshToken) {
        isTokenPresent(encryptedRefreshToken);
        //앞의 Bearer 삭제후 순수 RT 추출
        String pureRefreshToken = getBearerSubstring(encryptedRefreshToken);
        //redis에서 해당 키 검색해서 해당 토큰에 대응하는 key 추출
        String userId = redisService.getValues(pureRefreshToken);

        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        //jwt 생성
        String newAccessToken = getAccessToken(user);

        return "Bearer " + newAccessToken;
    }

    private String getAccessToken( User user) {
        return jwtUtil.createJwt(user.getUserId(), user.getPassword(), 86400000 * 7L);
    }

    private static String getBearerSubstring(String encryptedRefreshToken) {
        return encryptedRefreshToken.substring(7);
    }

    private void isTokenPresent(String encryptedRefreshToken) {
        if (encryptedRefreshToken == null) {
            throw new SecurityJwtNotFoundException(HttpExceptionCode.JWT_NOT_FOUND);
        }
    }


}
