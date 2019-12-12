package com.squeaker.entry.domain.payload.response.user;

import com.squeaker.entry.domain.payload.response.TwittResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponse {
    private Integer uuid;
    private String userId;
    private String userName;
    private String userIntro;
    private String userImage;
    private List<TwittResponse> timeLine;
    private List<FollowResponse> follower;
    private List<FollowResponse> following;

    @Builder
    public UserResponse(Integer uuid, String userId, String userName, String userIntro, String userImage,
                        List<TwittResponse> timeLine, List<FollowResponse> follower, List<FollowResponse> following) {
        this.uuid = uuid;
        this.userId = userId;
        this.userName = userName;
        this.userIntro = userIntro;
        this.userImage = userImage;
        this.timeLine = timeLine;
        this.follower = follower;
        this.following = following;
    }
}
