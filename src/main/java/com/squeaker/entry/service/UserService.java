package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.payload.request.UserSignUp;
import com.squeaker.entry.domain.payload.response.user.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    void authEmail(String email);
    void validEmail(String email, String code);
    UserResponse getUserInfo(String token);
    UserResponse getUser(Integer uuid);
    void signUp(UserSignUp userSignUp);
    void changeUser(User user, MultipartFile file);
    void deleteUser(String token);
}
