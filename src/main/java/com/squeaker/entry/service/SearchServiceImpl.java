package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.payload.response.SearchUserResponse;
import com.squeaker.entry.domain.repository.UserRepository;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SearchServiceImpl implements SearchService {

    private static final String IMAGE_DIR = "/home/ubuntu/server/images/user/";
    // private static final String IMAGE_DIR = "D:/Squeaker/user/";

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
            if(user.getUserPrivate() == 1) continue;
            String imageName = "jpg";
            for(File file : Objects.requireNonNull(new File(IMAGE_DIR).listFiles())) {
                String fullFileName = file.getName();
                String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
                if(fileName.equals(user.getUserId())) {
                    imageName = fullFileName.substring(fullFileName.lastIndexOf(".")+1);
                    break;
                }
            }
            list.add(
                    SearchUserResponse.builder()
                    .uuid(u.getUuid())
                    .userId(u.getUserId())
                    .userName(u.getUserName())
                    .userIntro(u.getUserIntro())
                    .userImage(u.getUserId() + "." + imageName)
                    .build()
            );
        }
        return list;
    }
}
