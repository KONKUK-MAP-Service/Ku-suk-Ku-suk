package com.cona.KUsukKusuk.user.service;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.bookmark.repository.BookmarkRepository;
import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.repository.CommentRepository;
import com.cona.KUsukKusuk.email.service.EmailService;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.exception.custom.security.IncorrectRefreshTokenException;
import com.cona.KUsukKusuk.global.exception.custom.security.SecurityJwtNotFoundException;
import com.cona.KUsukKusuk.global.redis.RedisService;
import com.cona.KUsukKusuk.global.security.JWTUtil;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.like.repository.UserLikeRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.BoomarkLikeResponseDto;
import com.cona.KUsukKusuk.user.dto.PageInfo;
import com.cona.KUsukKusuk.user.dto.UpdateUserProfileRequest;
import com.cona.KUsukKusuk.user.dto.UserJoinRequest;
import com.cona.KUsukKusuk.user.dto.UserProfileResponse;
import com.cona.KUsukKusuk.user.exception.NickNameAlreadyExistException;
import com.cona.KUsukKusuk.user.exception.PasswordNotMatchException;
import com.cona.KUsukKusuk.user.exception.UserExistException;
import com.cona.KUsukKusuk.user.exception.UserIdAlreadyExistException;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final SpotRepository spotRepository;
    private final UserLikeRepository userLikeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final RedisService redisService;
    private final EmailService mailService;
    private final JWTUtil jwtUtil;

    public User save(UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.findByUserId(user.getUserId())
                .ifPresent(m -> {
                    throw new UserIdAlreadyExistException();
                });

        userRepository.findByNickname(user.getNickname())
                .ifPresent(m -> {
                    throw new NickNameAlreadyExistException();
                });

        checkDuplicatedEmail(user.getEmail());

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

        if (!redisService.checkExistsValue(userId)) {
            throw new IncorrectRefreshTokenException();
        }
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

        if (!member.getEmail().equals(email)) {
            throw new UserNotFoundException(HttpExceptionCode.EMAIL_USER_NOT_EQUAL);
        }

        String newPassword = generateNewPassword();
        member.setPassword(bCryptPasswordEncoder.encode(newPassword));

        member.setNoCryptpassword(newPassword);
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
    public User findUserByUserid(String username) {
        return userRepository.findByUserId(username)
                .orElseThrow(() -> new UserNotFoundException(HttpExceptionCode.USER_NOT_FOUND));
    }
    private void checkDuplicatedEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new UserExistException(HttpExceptionCode.EMAIL_ALREADY_EXIST);
                });
    }
    public User updateUserProfile(String userId, UpdateUserProfileRequest request) {
        User currentUser = findUserByUserid(userId);

        if (!currentUser.getNickname().equals(request.nickname())) {
            userRepository.findByNickname(request.nickname())
                    .ifPresent(m -> {
                        throw new NickNameAlreadyExistException();
                    });
        }

        if (!currentUser.getEmail().equals(request.email())) {
            checkDuplicatedEmail(request.email());
        }

        if (!currentUser.getUserId().equals(request.userid())) {
            userRepository.findByUserId(request.userid())
                    .ifPresent(m -> {
                        throw new UserIdAlreadyExistException();
                    });
            currentUser.setUserId(request.userid());
        }

        if (request.password() != null && !request.password().isEmpty()) {
            currentUser.setPassword(bCryptPasswordEncoder.encode(request.password()));
            currentUser.setNoCryptpassword(request.password());
        }

        currentUser.setNickname(request.nickname());
        currentUser.setEmail(request.email());
        userRepository.save(currentUser);

        return currentUser;
    }
    @Transactional
    public void deleteUser(String userId) {
        User user = findUserByUserid(userId);

        // 댓글 삭제
        List<Comment> commentsToDelete = user.getComments();
        commentsToDelete.forEach(comment -> comment.getSpot().getComments().remove(comment));
        commentRepository.deleteAll(commentsToDelete);

        // 장소 삭제
        List<Spot> spotsToDelete = user.getSpots();
        spotRepository.deleteAll(spotsToDelete);

        // 즐겨찾기 삭제
        List<Bookmark> bookmarksToDelete = user.getBookmarks();
        bookmarkRepository.deleteAll(bookmarksToDelete);

        // 좋아요 삭제
        List<UserLike> userLikesToDelete = user.getUserLikes();
        userLikeRepository.deleteAll(userLikesToDelete);




        userRepository.delete(user);
    }
    public UserProfileResponse getCurrentUserProfile() {
        String userId = getUsernameBySecurityContext();
        User user = findUserByUserid(userId);
        return UserProfileResponse.of(user);
    }

    public List<BoomarkLikeResponseDto> getBookmarkandLikes(int pageNumber, int pageSize) {
        String username = getUsernameBySecurityContext();
        User user = findUserByUserid(username);

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        List<UserLike> userLikes = userLikeRepository.findByUser(user);

        List<Spot> bookmarkedSpots;
        List<Spot> likedSpots = new ArrayList<>();

        if (bookmarks != null) {
            bookmarkedSpots = bookmarks.stream()
                    .map(Bookmark::getSpot)
                    .collect(Collectors.toList());
        } else {
            bookmarkedSpots = new ArrayList<>();
        }

        if (userLikes != null) {
            likedSpots = userLikes.stream()
                    .map(UserLike::getSpot)
                    .collect(Collectors.toList());
        }

        List<Spot> distinctSpots = Stream.concat(bookmarkedSpots.stream(), likedSpots.stream())
                .distinct()
                .collect(Collectors.toList());

        int start = Math.min(pageNumber * pageSize, distinctSpots.size());
        int end = Math.min((pageNumber + 1) * pageSize, distinctSpots.size());

        if (start > end) {
            start = end;
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalElements(distinctSpots.size());
        pageInfo.setPage(pageNumber+1);
        pageInfo.setSize(pageSize);
        pageInfo.setTotalPages((int) Math.ceil((double) distinctSpots.size() / pageSize));

        List<Spot> pagedDistinctSpots = distinctSpots.subList(start, end);

        List<Spot> finalLikedSpots = likedSpots;

        return pagedDistinctSpots.stream()
                .map(spot -> BoomarkLikeResponseDto.of(spot, bookmarkedSpots.contains(spot), finalLikedSpots.contains(spot), pageInfo))
                .collect(Collectors.toList());
    }



}
