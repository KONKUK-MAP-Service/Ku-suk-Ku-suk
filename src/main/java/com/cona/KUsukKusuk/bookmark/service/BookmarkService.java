package com.cona.KUsukKusuk.bookmark.service;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.bookmark.dto.BookmarkResponseDto;
import com.cona.KUsukKusuk.bookmark.dto.UserBookmarkResponse;
import com.cona.KUsukKusuk.bookmark.exception.BookmarkException;
import com.cona.KUsukKusuk.bookmark.repository.BookmarkRepository;
import com.cona.KUsukKusuk.global.exception.HttpExceptionCode;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.spot.exception.SpotNotFoundException;
import com.cona.KUsukKusuk.spot.repository.SpotRepository;
import com.cona.KUsukKusuk.user.domain.User;
import com.cona.KUsukKusuk.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
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

    public void deleteBookmark(Long spotId) {
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);

        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException(HttpExceptionCode.SPOT_NOT_FOUND));

        Bookmark bookmark = bookmarkRepository.findByUserAndSpot(user, spot)
                .orElseThrow(()->new BookmarkException(HttpExceptionCode.BOOK_MARK_NOT_EXIST));

        bookmarkRepository.delete(bookmark);
    }


    public List<BookmarkResponseDto> getUserBookmarks() {
        String username = userService.getUsernameBySecurityContext();
        User user = userService.findUserByUserid(username);
        List<Bookmark> userBookmarks = bookmarkRepository.findByUser(user);
        if (userBookmarks == null || userBookmarks.isEmpty()) {
            throw new BookmarkException(HttpExceptionCode.BOOK_MARK_NOT_EXIST);
        }


        return userBookmarks.stream()
                .map(bookmark -> BookmarkResponseDto.of(bookmark,bookmark.getSpot()))
                .collect(Collectors.toList());
    }
}
