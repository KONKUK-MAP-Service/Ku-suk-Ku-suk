package com.cona.KUsukKusuk.like.controller;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import com.cona.KUsukKusuk.like.dto.LikeRequest;
import com.cona.KUsukKusuk.like.dto.UnlikeRequest;
import com.cona.KUsukKusuk.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Tag(name = "좋아요 컨트롤러", description = "좋아요 도메인에 대한 컨트롤러 입니다.")

public class LikeController {
    private final LikeService likeService;

    @PostMapping("/add")
    @Operation(summary = "좋아요 등록", description = "좋아요 등록을 수행합니다.")

    public HttpResponse<String> addLike(@RequestBody LikeRequest likeDto) {
        likeService.addLike(likeDto);
        return HttpResponse.okBuild("좋아요 등록이 성공적으로 진행되었습니다.");
    }
    @DeleteMapping("/remove")
    @Operation(summary = "좋아요 삭제", description = "좋아요 삭제을 수행합니다.")

    public HttpResponse<String> removeLike(@RequestBody UnlikeRequest unlikeDto) {
        likeService.removeLike(unlikeDto);
        return HttpResponse.okBuild("좋아요 삭제가 성공적으로 진행되었습니다.");
    }


}
