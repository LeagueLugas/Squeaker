package com.squeaker.entry.domain.entitys;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @Column(nullable = false, unique = true, length = 30)
    private String userId;

    @Column(nullable = false, length = 30)
    private String userPw;

    @Column(nullable = false, length = 12)
    private String userName;

    @Column(length = 60)
    private String userIntro;

    @Column
    private String userRefreshToken;

    @Column(nullable = false)
    private Integer userPrivate;

    @Builder
    public User(String userId, String userPw, String userName,
                String userIntro, String userRefreshToken, Integer userPrivate) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userIntro = userIntro;
        this.userRefreshToken = userRefreshToken;
        this.userPrivate = userPrivate;
    }
}
