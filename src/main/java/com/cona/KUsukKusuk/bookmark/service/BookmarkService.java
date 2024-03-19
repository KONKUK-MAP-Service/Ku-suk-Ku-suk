package com.cona.KUsukKusuk.bookmark.service;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.bookmark.repository.BookmarkRepository;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final SpotRepository spotRepository;

    public void addBookmark(Long spotId) {
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException(HttpExceptionCode.SPOT_NOT_FOUND));

        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setSpot(spot);

        bookmarkRepository.save(bookmark);
    }
}
