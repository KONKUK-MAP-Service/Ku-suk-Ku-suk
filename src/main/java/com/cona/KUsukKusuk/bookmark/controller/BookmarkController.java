package com.cona.KUsukKusuk.bookmark.controller;

import com.cona.KUsukKusuk.bookmark.dto.BookmarkRequest;
import com.cona.KUsukKusuk.bookmark.service.BookmarkService;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/add")
    @Operation(summary = "북마트 등록", description = "북마크 등록을 수행합니다.")

    public HttpResponse<String> addBookmark(@RequestBody BookmarkRequest request) {
        bookmarkService.addBookmark(request.spotId());
        return HttpResponse.okBuild("장소를 북마크했습니다.");
    }
}