package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.payload.response.TokenResponse;
import com.squeaker.entry.domain.repository.UserRepository;
import com.squeaker.entry.exception.InvalidTokenException;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TokenResponse login(String userId, String userPw) {

        User user = userRepository.findByUserIdAndUserPw(userId, userPw);
        if(user == null) throw new UserNotFoundException();

        return new TokenResponse(user.getUserId());
    }

    @Override
    public TokenResponse refreshToken(String token) {
        int uuid = Integer.parseInt(JwtUtil.parseToken(token));
        User user = userRepository.findByUuidAndUserRefreshToken(uuid, token);
        if(user == null || !user.getUserRefreshToken().equals(token)) throw new InvalidTokenException();

        return new TokenResponse(uuid);
    }
}
