package com.cona.KUsukKusuk.bookmark.repository;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.spot.domain.Spot;
import com.cona.KUsukKusuk.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    void deleteAllByUser(User user);
    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndSpot(User user, Spot spot);

}
