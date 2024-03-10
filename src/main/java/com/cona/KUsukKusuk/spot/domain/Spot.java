package com.cona.KUsukKusuk.spot.domain;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.comment.Comment;
import com.cona.KUsukKusuk.global.domain.BaseEntity;
import com.cona.KUsukKusuk.like.UserLike;
import com.cona.KUsukKusuk.picture.Picture;
import com.cona.KUsukKusuk.user.domain.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Spot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "spot")
    private List<UserLike> userLikes = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<Picture> pictures = new ArrayList<>();

    //사진의 url들을 저장하기 위해 추가 (영속 대상에서 제외)
    @Transient
    private List<String> pictureUrls = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    private List<Bookmark> bookmarks = new ArrayList<>();
    @Column(nullable = false)
    private String spotName;
    @Column(nullable = false)
    private String longitude;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String review;

    @Column(nullable = true)
    private Long likes;
}
