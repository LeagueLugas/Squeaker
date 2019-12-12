package com.squeaker.entry.domain.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignIn {
    private String userId;
    private String userPw;
}
