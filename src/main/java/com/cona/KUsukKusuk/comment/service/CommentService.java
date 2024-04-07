package com.cona.KUsukKusuk.comment.service;

import com.cona.KUsukKusuk.comment.domain.Comment;
import com.cona.KUsukKusuk.comment.dto.CommentGetResponse;
import com.cona.KUsukKusuk.comment.dto.CommentListResponseDto;
import com.cona.KUsukKusuk.comment.dto.CommentPaginationResponse;
import com.cona.KUsukKusuk.comment.exception.CommentNotFoundException;
import com.cona.KUsukKusuk.comment.exception.CommentUserNotMatchedException;
import com.cona.KUsukKusuk.comment.repository.CommentRepository;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.dto.BoomarkLikeResponseDto;
import com.cona.KUsukKusuk.user.dto.PageInfo;
import com.cona.KUsukKusuk.user.service.UserService;
import java.util.Collections;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final SpotRepository spotRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, SpotRepository spotRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.spotRepository = spotRepository;
        this.userService = userService;
    }

    public Comment save(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    public User getCurrentUser() {
        String name = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(name);
        return user;
    }

    public Spot getCurrentSpot(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException());
        return spot;
    }

    public Comment getCurrentComment(String commentUserName, Spot spot, Long commentId)
            throws CommentNotFoundException, CommentUserNotMatchedException {
        List<Comment> commentList = spot.getComments();
        Comment wantToUpdate = null; // 초기화를 null로 설정
        for (Comment comment : commentList) {
            if (comment.getId().equals(commentId)) { // spot 내의 comment인지 확인
                wantToUpdate = comment;
                break; // 원하는 comment를 찾았으므로 루프 종료
            }
        }

        // wantToUpdate가 여전히 null인 경우 예외 처리
        if (wantToUpdate == null) {
            throw new CommentNotFoundException("Comment with id: " + commentId + " not found.");
        }

        //commentUserName과 comment의 작성자 일치 확인
        if (wantToUpdate.getUser().getUserId().equals(commentUserName)) {
            return wantToUpdate;
        } else {
            throw new CommentUserNotMatchedException("Don't have authority to update the comment.");
        }

    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }


    public List<CommentListResponseDto> getUserCommentsOfAllSpots(Long userId) {
        // 사용자가 쓴 comment만 가져오기
        User user = getCurrentUser();
        List<Comment> comments = commentRepository.findByUser(user);
        List<CommentListResponseDto> commentsByUser = new ArrayList<>();

        // 가져온 comment를 CommentListResponseDto로 변환하여 리스트에 추가
        for (Comment comment : comments) {
            CommentListResponseDto commentDto = CommentListResponseDto.builder()
                    .spotName(comment.getSpot().getSpotName())
                    .spotId(comment.getSpot().getId())
                    .review(comment.getSpot().getReview())
                    .CommentcreateDate(comment.getSpot().getCreatedDate())
                    .author(comment.getUser().getNickname())
                    .spotImageurl(comment.getSpot().getImageUrls().get(0))
                    .build();
            commentsByUser.add(commentDto);
        }

        return commentsByUser;
    }

    public List<CommentListResponseDto> getPagedComments( int
            pageNumber
            , int pageSize) {

        User user = getCurrentUser();
        List<Comment> comments = commentRepository.findByUser(user);
        System.out.println("comments.size() = " + comments.size());
        List<CommentListResponseDto> pagedResponse = new ArrayList<>();


        int start = Math.min(pageNumber * pageSize, comments.size());
        int end = Math.min((pageNumber + 1) * pageSize, comments.size());

        if (start >= comments.size()) {
            return Collections.emptyList(); // 페이지 범위를 벗어나면 빈 리스트 반환
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalElements(comments.size());
        pageInfo.setPage(pageNumber + 1);
        pageInfo.setSize(pageSize);
        pageInfo.setTotalPages((int) Math.ceil((double) comments.size() / pageSize));

        List<Comment> pagedcomments = comments.subList(start, end);

        return pagedcomments.stream()
                .map(comment -> CommentListResponseDto.of(comment, pageInfo))
                .collect(Collectors.toList());
    }
}