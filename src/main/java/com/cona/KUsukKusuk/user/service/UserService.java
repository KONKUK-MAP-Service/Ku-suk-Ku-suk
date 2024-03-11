package com.cona.KUsukKusuk.user.service;

import com.cona.KUsukKusuk.email.service.EmailService;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.exception.custom.security.IncorrectRefreshTokenException;
import com.cona.KUsukKusuk.global.exception.custom.security.SecurityJwtNotFoundException;
import com.cona.KUsukKusuk.global.redis.RedisService;
import com.cona.KUsukKusuk.global.security.JWTUtil;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.dto.UserProfileResponse;
import com.cona.KUsukKusuk.user.exception.PasswordNotMatchException;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisService redisService;
    private final EmailService mailService;
    private final JWTUtil jwtUtil;

    public User save(UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public String logout(String encryptedRefreshToken) {
        this.isTokenPresent(encryptedRefreshToken);
        //RT가 레디스에 저장된값이랑 일치하는지 확인
        String userId = redisService.getValues(encryptedRefreshToken);
        if (!redisService.checkExistsValue(userId)) {
            throw new IncorrectRefreshTokenException();
        }
        //RT 를 레디스에서 삭제
        redisService.deleteValues(encryptedRefreshToken);

        String result = addToBlacklist(encryptedRefreshToken);
        return result;

    }


    private String addToBlacklist(String encryptedRefreshToken) {
        String blacklistKey = encryptedRefreshToken;

        redisService.setValues(blacklistKey, "blacklist", Duration.ofMillis(60 * 60 * 100L));

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
        //6분간 유지되는 AT 재발급
        return jwtUtil.createJwt(user.getUserId(), user.getPassword(), 60 * 60 * 100L);
    }

    private static String getBearerSubstring(String encryptedRefreshToken) {
        return encryptedRefreshToken.substring(7);
    }

    private void isTokenPresent(String encryptedRefreshToken) {
        if (encryptedRefreshToken == null) {
            throw new SecurityJwtNotFoundException(HttpExceptionCode.JWT_NOT_FOUND);
        }
    }
    public String findPassword(String userId, String email) {
        User member=userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpExceptionCode.USERID_NOT_FOUND));

        if (member.getEmail() != email) {
            throw new UserNotFoundException(HttpExceptionCode.EMAIL_USER_NOT_EQUAL);
        }

        String newPassword = generateNewPassword();
        member.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(member);

        String title = "쿠석쿠석 임시 비밀번호 발급";
        mailService.sendEmail(email, title, "새로운 비밀번호 : " + newPassword);

        return email;
    }
    private String generateNewPassword() {
        String randomString = RandomStringUtils.randomAlphanumeric(9);

        int randomIndex = (int) (Math.random() * 9);

        char randomNumber = (char) ('0' + (int) (Math.random() * 10));
        char[] newPasswordChars = randomString.toCharArray();
        newPasswordChars[randomIndex] = randomNumber;

        return new String(newPasswordChars);
    }

    public String getUsernameBySecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getName();
    }

    public void checkPassword(String enteredPassword) {
        String username = getUsernameBySecurityContext();
        String storedPassword = userRepository.findByUserId(username)
                .map(User::getPassword)
                .orElseThrow(() -> new UserNotFoundException(HttpExceptionCode.USER_NOT_FOUND));

        if( ! bCryptPasswordEncoder.matches(enteredPassword, storedPassword)){
            throw new PasswordNotMatchException();
        }
    }
    public User findMemberByUsername(String username) {
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new UserNotFoundException(HttpExceptionCode.USER_NOT_FOUND));
    }


}
