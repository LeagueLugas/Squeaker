package com.squeaker.entry.domain.payload.response;

import com.squeaker.entry.utils.JwtUtil;
import lombok.Getter;

@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse(Object userId) {
        this.accessToken = JwtUtil.getAccessToken(userId);
        this.refreshToken = JwtUtil.getRefreshToken(userId);
    }
}
