package com.cona.KUsukKusuk.user.domain;

import com.cona.KUsukKusuk.bookmark.domain.Bookmark;
import com.cona.KUsukKusuk.comment.domain.Comment;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;
    @Column(nullable = true)
    private String email;
    @Column(nullable = false)
    private String nickname;
    @Column
    private String profileimage;

    private String noCryptpassword;

    @OneToMany(mappedBy = "user")
   private List<Bookmark> bookmarks = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<UserLike> userLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Spot> spots = new ArrayList<>();

}
