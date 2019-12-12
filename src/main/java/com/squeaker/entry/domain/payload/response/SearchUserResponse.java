package com.squeaker.entry.domain.payload.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchUserResponse {
    private Integer uuid;
    private String userId;
    private String userName;
    private String userIntro;
    private String userImage;

    @Builder
    public SearchUserResponse(Integer uuid, String userId, String userName, String userIntro, String userImage) {
        this.uuid = uuid;
        this.userId = userId;
        this.userName = userName;
        this.userIntro = userIntro;
        this.userImage = userImage;
    }
}
