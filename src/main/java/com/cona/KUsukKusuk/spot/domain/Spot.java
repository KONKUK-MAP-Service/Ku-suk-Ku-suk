package com.cona.KUsukKusuk.spot.domain;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.comment.Comment;
import com.cona.KUsukKusuk.global.domain.BaseEntity;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.picture.Picture;
import com.cona.KUsukKusuk.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Spot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "spot")
    private List<UserLike> userLikes = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<Bookmark> bookmarks = new ArrayList<>();

    private String spotName;

    private String longitude;
    private String latitude;
    private String review;

    private Long like;
}
