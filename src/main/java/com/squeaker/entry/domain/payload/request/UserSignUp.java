package com.squeaker.entry.domain.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class UserSignUp {
    private String userId;
    private String userPw;
    private String userName;
    private String userIntro;
    private MultipartFile multipartFile;

    @Builder
    public UserSignUp(String userId, String userPw, String userName,
                      String userIntro, MultipartFile multipartFile) {
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userIntro = userIntro;
        this.multipartFile = multipartFile;
    }
}
