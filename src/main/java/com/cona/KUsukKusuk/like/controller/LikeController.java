package com.cona.KUsukKusuk.like.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.like.dto.LikeRequest;
import com.cona.KUsukKusuk.like.dto.UnlikeRequest;
import com.cona.KUsukKusuk.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/add")
    public HttpResponse<String> addLike(@RequestBody LikeRequest likeDto) {
        likeService.addLike(likeDto);
        return HttpResponse.okBuild("좋아요 등록이 성공적으로 진행되었습니다.");
    }
    @DeleteMapping("/remove")
    public HttpResponse<String> removeLike(@RequestBody UnlikeRequest unlikeDto) {
        likeService.removeLike(unlikeDto);
        return HttpResponse.okBuild("북마크 삭제가 성공적으로 진행되었습니다.");
    }


}
