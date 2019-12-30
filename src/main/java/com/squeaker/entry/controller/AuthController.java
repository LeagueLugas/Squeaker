package com.squeaker.entry.controller;

import com.squeaker.entry.domain.payload.request.UserSignIn;
import com.squeaker.entry.domain.payload.response.TokenResponse;
import com.squeaker.entry.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @PostMapping
    public TokenResponse signIn(@RequestBody @NotNull UserSignIn userSignIn) {
        return authService.login(userSignIn.getUserId(), userSignIn.getUserPw());
    }

    @PutMapping
    public TokenResponse refreshToken(@RequestHeader("Authorization") String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

}
