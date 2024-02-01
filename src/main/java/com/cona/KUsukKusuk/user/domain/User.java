package com.cona.KUsukKusuk.user.domain;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.comment.Comment;
import com.cona.KUsukKusuk.global.domain.BaseEntity;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.spot.domain.Spot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    @OneToMany(mappedBy = "user")
   private List<Bookmark> bookmarks = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<UserLike> userLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Spot> spots = new ArrayList<>();

}
