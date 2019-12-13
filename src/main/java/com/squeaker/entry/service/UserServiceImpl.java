package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.EmailAuth;
import com.squeaker.entry.domain.entitys.Follow;
import com.squeaker.entry.domain.entitys.Twitt;
import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.payload.request.UserSignUp;
import com.squeaker.entry.domain.payload.response.AuthCodeResponse;
import com.squeaker.entry.domain.payload.response.TwittResponse;
import com.squeaker.entry.domain.payload.response.user.FollowResponse;
import com.squeaker.entry.domain.payload.response.user.UserResponse;
import com.squeaker.entry.domain.repository.*;
import com.squeaker.entry.exception.*;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final String IMAGE_DIR = "/home/ubuntu/server/Squeaker/images/user/";

    private AuthMailRepository authMailRepository;
    private UserRepository userRepository;
    private FollowRepository followRepository;
    private TwittRepository twittRepository;
    private ImageRepository imageRepository;
    private TwittLikeRespository twittLikeRespository;
    private CommentRepository commentRepository;

    public UserServiceImpl(AuthMailRepository authMailRepository, UserRepository userRepository, FollowRepository followRepository, TwittRepository twittRepository, ImageRepository imageRepository, TwittLikeRespository twittLikeRespository, CommentRepository commentRepository) {
        this.authMailRepository = authMailRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.twittRepository = twittRepository;
        this.imageRepository = imageRepository;
        this.twittLikeRespository = twittLikeRespository;
        this.commentRepository = commentRepository;
    }

    @Override
    public AuthCodeResponse authEmail(String email) {
        String uuid = UUID.randomUUID().toString();
        authMailRepository.save(
                EmailAuth.builder()
                        .authEmail(email)
                        .authCode(uuid)
                        .authState("UnAuthorized")
                        .build()
        );

        new Thread(() -> {
            try {
                Thread.sleep(300000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            EmailAuth emailAuth = authMailRepository.findByAuthEmail(email);
            authMailRepository.delete(emailAuth);
        });

        return new AuthCodeResponse(uuid);
    }

    @Override
    public void validEmail(String code) {
        EmailAuth auth = authMailRepository.findByAuthCode(code);
        if(auth == null) throw new InvalidAuthCodeException();

        auth.setAuthState("Authorized");
        authMailRepository.save(auth);
    }

    @Override
    public UserResponse getUserInfo(String token) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        if(user == null) throw new UserNotFoundException();


        return userResponse(user);
    }

    @Override
    public UserResponse getUser(Integer uuid) {
        User user = userRepository.findByUuid(uuid);
        if(user == null) throw new UserNotFoundException();

        return userResponse(user);
    }

    @Override
    public void signUp(UserSignUp userSignUp) {

        if(userRepository.existsByUserId(userSignUp.getUserId())) throw new UserAlreadyExistsException();
        EmailAuth auth = authMailRepository.findByAuthEmail(userSignUp.getUserId());
        if(auth == null) throw new InvalidAuthEmailException();
        if(auth.getAuthState().equals("UnAuthorized")) throw new InvalidAuthCodeException();

        if(userSignUp.getMultipartFile() != null) {
            File file = new File(IMAGE_DIR + userSignUp.getUserId() + ".jpg");
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
                userSignUp.getMultipartFile().transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new InvalidFileException();
            }
        }

        authMailRepository.delete(authMailRepository.findByAuthEmail(userSignUp.getUserId()));
        userRepository.save(
                User.builder()
                .userId(userSignUp.getUserId())
                .userPw(userSignUp.getUserPw())
                .userName(userSignUp.getUserName())
                .userIntro(userSignUp.getUserIntro())
                .userPrivate(0)
                .build()
        );
    }

    @Override
    public void changeUser(User info, MultipartFile multipartFile) {
        User user = userRepository.findByUserId(info.getUserId());
        if(user == null) throw new UserNotFoundException();

        if (info.getUserPw() != null) user.setUserPw(info.getUserPw());
        if (info.getUserName() != null) user.setUserName(info.getUserName());
        if (info.getUserIntro() != null) user.setUserIntro(info.getUserIntro());
        if (info.getUserPrivate() != null) user.setUserPrivate(info.getUserPrivate());

        if(multipartFile != null) {
            File file = new File(IMAGE_DIR + user.getUserId() + ".jpg");
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
                multipartFile.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new InvalidFileException();
            }
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(String token) {
        String userId = JwtUtil.parseToken(token);
        User user = userRepository.findByUserId(userId);
        if(user == null) throw new UserNotFoundException();

        File file = new File(IMAGE_DIR + user.getUserId() + ".jpg");
        file.delete();

        userRepository.delete(user);
    }

    private UserResponse userResponse(User user) {
        List<TwittResponse> twitts = new ArrayList<>();
        List<FollowResponse> follower = new ArrayList<>();
        List<FollowResponse> following = new ArrayList<>();

        for(Twitt twitt : twittRepository.findByTwittUidOrderByTwittDateDesc(user.getUuid())) {
            twitts.add(TwittServiceImpl.getTwittInfo(user, twitt, imageRepository, twittLikeRespository, commentRepository));
        }
        for(Follow follows : followRepository.findByFollowerOrFollowing(user.getUuid(), user.getUuid())) {
            if(follows.getFollower().equals(user.getUuid())) {
                User follow = userRepository.findByUuid(follows.getFollowing());
                following.add(
                        FollowResponse.builder()
                                .uuid(follow.getUuid())
                                .userId(follow.getUserId())
                                .userName(follow.getUserName())
                                .userImage(IMAGE_DIR + follow.getUserId()+".jpg")
                                .build()
                );
            } else {
                User follow = userRepository.findByUuid(follows.getFollower());
                follower.add(
                        FollowResponse.builder()
                                .uuid(follow.getUuid())
                                .userId(follow.getUserId())
                                .userName(follow.getUserName())
                                .userImage(follow.getUserId()+".jpg")
                                .build()
                );
            }
        }

        return UserResponse.builder()
                .uuid(user.getUuid())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userIntro(user.getUserIntro())
                .userImage(user.getUserId()+".jpg")
                .timeLine(twitts)
                .follower(follower)
                .following(following)
                .build();
    }
}
