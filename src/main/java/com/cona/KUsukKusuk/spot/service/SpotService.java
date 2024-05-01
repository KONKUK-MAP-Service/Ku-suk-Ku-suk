package com.cona.KUsukKusuk.spot.service;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.bookmark.repository.BookmarkRepository;
import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.global.s3.ImageUrlConverter;
import com.cona.KUsukKusuk.global.s3.S3Service;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.like.repository.UserLikeRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.dto.CommentResponse;
import com.cona.KUsukKusuk.spot.dto.SpotDetailResponse;
import com.cona.KUsukKusuk.spot.dto.SpotGetResponse;
import com.cona.KUsukKusuk.spot.dto.SpotUpdateRequest;
import com.cona.KUsukKusuk.spot.dto.SpotUploadRequest;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.exception.UserNotFoundException;
import com.cona.KUsukKusuk.user.repository.UserRepository;
import com.cona.KUsukKusuk.user.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserLikeRepository userLikeRepository;
    private final S3Service s3Service;


    public Spot uploadSpot(List<MultipartFile> images, SpotUploadRequest spotUploadRequest) throws IOException {

        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(userId);
        Spot spot = spotUploadRequest.toEntity();
        spot.setUser(user);

        user.getSpots().add(spot);



            if(!images.get(0).isEmpty()){

                System.out.println("images size= " + images.size());

                List<String> imageUrls = s3Service.uploadImages(images, userId);
                spot.setImageUrls(imageUrls);
            }



        Spot savedSpot = spotRepository.save(spot);
        userRepository.save(user);
        return savedSpot;
    }
    public List<SpotGetResponse> getAllSpots() {
        String userId = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(userId);
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        List<UserLike> userLikes = userLikeRepository.findByUser(user);

        List<Spot> spots = spotRepository.findAll();
        return spots.stream()
                .map(spot -> {
                    boolean isBookmarkedByUser = bookmarks != null && bookmarks.stream()
                            .anyMatch(bookmark -> bookmark.getSpot().equals(spot));
                    boolean isLikedByUser = userLikes != null && userLikes.stream()
                            .anyMatch(like -> like.getSpot().equals(spot));
                    return SpotGetResponse.of(spot, spot.getUser().equals(user), isBookmarkedByUser, isLikedByUser);
                })
                .collect(Collectors.toList());
    }
    public SpotDetailResponse getSpotDetails(Long spotId) {
        String username = SecurityContextHolder.getContext().getAuthentication()
                .getName();

        Boolean isBookmark=false;
        Boolean isLike=false;

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());

        if (!username.equals("anonymousUser")) {

            User user = userService.findUserByUserid(username);
            List<UserLike> userLikes = userLikeRepository.findByUser(user);
            List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);

            isLike = userLikes != null && userLikes.stream().anyMatch(like -> like.getSpot().equals(spot));

            isBookmark = bookmarks != null && bookmarks.stream().anyMatch(bookmark -> bookmark.getSpot().equals(spot));

        }



        boolean isUsersOwnSpot = spot.getUser().getId().equals(false);
        return SpotDetailResponse.fromSpot(spot,isBookmark,isLike);
    }
    public List<SpotGetResponse> getAllPublicSpots() {

        List<Spot> spots = spotRepository.findAll();
        return spots.stream()
                .map(spot -> SpotGetResponse.of(spot, false,false,false))
                .collect(Collectors.toList());
    }
    public Spot updateSpot(Long spotId,List<MultipartFile> images, SpotUpdateRequest spotUpdateRequest) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotFoundException(HttpExceptionCode.USER_LOGIN_PERMIT_FAIL);
        }
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());
        if (!spot.getUser().equals(user)) {
            throw new UserNotFoundException(HttpExceptionCode.USER_NOT_MATCH);
        }

        String[] deleteImageUrls = spotUpdateRequest.deleteImageUrls();
        List<String> imageUrls = spot.getImageUrls();

        for (String deleteImageUrl : deleteImageUrls) {
            String s3ImageUrl = ImageUrlConverter.convertToS3Url(deleteImageUrl);
            if (imageUrls.contains(s3ImageUrl)) {
                imageUrls.remove(s3ImageUrl);
                spot.setImageUrls(imageUrls);
                // S3에서 이미지 삭제
                s3Service.deleteImagebyUrl(user, s3ImageUrl);
            }
        }

        if (!images.get(0).isEmpty()) {
            //이미지가 존재할 경우 기존 이미지 전부 삭제후 새로운 이미지 업로드
            s3Service.deleteSpotImages(spot,user);
            List<String> imagesurl = s3Service.uploadImages(images, username);
            spot.setImageUrls(imagesurl);
        }

        spot.setSpotName(spotUpdateRequest.spotName());
        spot.setReview(spotUpdateRequest.review());

        Spot saved = spotRepository.save(spot);

        return saved;
    }


    public void deleteSpot(Long spotId) {

        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());
        if (!spot.getUser().equals(user)) {
            throw new UserNotFoundException(HttpExceptionCode.USER_NOT_MATCH);
        }
        s3Service.deleteSpotImages(spot,user);

        spotRepository.delete(spot);
    }
    public List<CommentResponse> getSpotComments(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());

        String username = userService.getUsernameBySecurityContext();
        boolean currentUserLoggedIn = !username.equals("anonymousUser");


        List<Comment> comments = spot.getComments();
        return comments.stream()
                .map(comment -> mapToCommentResponse(comment, currentUserLoggedIn, username))
                .collect(Collectors.toList());
    }

    private CommentResponse mapToCommentResponse(Comment comment, boolean currentUserLoggedIn, String username) {
        boolean deletable = currentUserLoggedIn && comment.getUser().getUserId().equals(username);

        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getNickname(),
                deletable,
                comment.getCreatedDate(),
                comment.getUser().getProfileimage()
        );
    }







}
