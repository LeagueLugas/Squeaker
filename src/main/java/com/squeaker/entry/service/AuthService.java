package com.squeaker.entry.service;

import com.squeaker.entry.domain.payload.response.TokenResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    TokenResponse login(String userId, String userPw);
    TokenResponse refreshToken(String token);
}
