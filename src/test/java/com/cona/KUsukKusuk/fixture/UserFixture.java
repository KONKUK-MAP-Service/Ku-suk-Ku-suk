package com.cona.KUsukKusuk.fixture;

import com.cona.KUsukKusuk.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public enum UserFixture {

    DEFAULT(
            1L,
            "konkuk",
            "vdongv1620",
            "donghoon141@naver.com",
            "최동훈"
    );


    private Long id;

    private String userId;

    private String password;
    private String email;
    private String nickname;

    UserFixture(Long id, String userId, String password, String email, String nickname) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public User getUser() {
        return User.builder()
                .id(id)
                .userId(userId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }
}

