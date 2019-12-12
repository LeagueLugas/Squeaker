package com.squeaker.entry.domain.payload.response.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponse {
    private Integer uuid;
    private String userId;
    private String userName;
    private String userImage;

    @Builder
    public FollowResponse(Integer uuid, String userId, String userName, String userImage) {
        this.uuid = uuid;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
    }
}
