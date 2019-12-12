package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.payload.response.SearchUserResponse;
import com.squeaker.entry.domain.repository.UserRepository;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private static final String IMAGE_DIR = "D:/Squeaker/user/";

    private UserRepository userRepository;

    public SearchServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SearchUserResponse> searchUser(String token, String userName) {
        List<SearchUserResponse> list = new ArrayList<>();
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));

        if(user == null) throw new UserNotFoundException();

        List<User> users = userRepository.findAllByUserIdContainingOrUserNameContaining(userName, userName);
        for(User u : users) {
            list.add(
                    SearchUserResponse.builder()
                    .uuid(u.getUuid())
                    .userId(u.getUserId())
                    .userName(u.getUserName())
                    .userIntro(u.getUserIntro())
                    .userImage(IMAGE_DIR + u.getUserId() + ".jpg")
                    .build()
            );
        }
        return list;
    }
}
