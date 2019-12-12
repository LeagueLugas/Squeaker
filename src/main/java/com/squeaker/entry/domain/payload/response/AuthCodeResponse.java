package com.squeaker.entry.domain.payload.response;

import lombok.Getter;

@Getter
public class AuthCodeResponse {
    private String code;

    public AuthCodeResponse(String code) {
        this.code = code;
    }
}
