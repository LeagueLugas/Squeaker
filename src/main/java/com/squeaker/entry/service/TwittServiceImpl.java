package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.*;
import com.squeaker.entry.domain.payload.response.TwittResponse;
import com.squeaker.entry.domain.repository.*;
import com.squeaker.entry.exception.InvalidFileException;
import com.squeaker.entry.exception.TwittNotFoundException;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.exception.UserNotMatchException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TwittServiceImpl implements TwittService {

    private static final String IMAGE_DIR = "/home/ubuntu/server/Squeaker/images/twitt/";

    private UserRepository userRepository;
    private TwittRepository twittRepository;
    private FollowRepository followRepository;
    private ImageRepository imageRepository;
    private TwittLikeRespository twittLikeRespository;
    private CommentRepository commentRepository;

    public TwittServiceImpl(UserRepository userRepository, TwittRepository twittRepository, FollowRepository followRepository, ImageRepository imageRepository, TwittLikeRespository twittLikeRespository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.twittRepository = twittRepository;
        this.followRepository = followRepository;
        this.imageRepository = imageRepository;
        this.twittLikeRespository = twittLikeRespository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<TwittResponse> getTwitts(String token, Integer count) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        if(user == null) throw new UserNotFoundException();
        List<Integer> relationUser = new ArrayList<>();

        relationUser.add(user.getUuid());
        for(Follow follow : followRepository.findByFollower(user.getUuid()))
            relationUser.add(follow.getFollowing());

        List<TwittResponse> twitts = new ArrayList<>();
        List<Twitt> twittList = twittRepository.findByTwittUidInOrderByTwittDateDesc(relationUser);

        for(int i = (count-1)*10; i < (count*10)-1; i++) {
            try {
                twitts.add(getTwittInfo(user, twittList.get(i), imageRepository, twittLikeRespository, commentRepository));
            } catch (Exception e){
                break;
            }
        }
        return twitts;
    }

    @Override
    public TwittResponse getTwitt(String token, Integer twittId) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        Twitt twitt = twittRepository.findByTwittId(twittId);
        if(twitt == null) throw new TwittNotFoundException();

        return getTwittInfo(user, twitt, imageRepository, twittLikeRespository, commentRepository);
    }

    @Override
    public void addTwitt(String token, String content, MultipartFile[] files) {
        File file;
        FileWriter fileWriter;
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        if(user == null) throw new UserNotFoundException();

        Twitt twitt = twittRepository.save(
                Twitt.builder()
                        .twittUid(user.getUuid())
                        .twittContent(content)
                        .twittDate(new Date().getTime())
                        .build()
        );
        if(files == null) return;

        for(int i = 0; i < files.length; i++) {
            Image image = imageRepository.save(
                    Image.builder()
                    .twittId(twitt.getTwittId())
                    .imageName("image_"+ twitt.getTwittId() +"-"+ i +".jpg")
                    .build()
            );
            file = new File(IMAGE_DIR + image.getImageName());
            try {
                fileWriter = new FileWriter(file);
                fileWriter.close();
                files[i].transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new InvalidFileException();
            }
        }
    }

    @Override
    public void deleteTwit(String token, Integer twittId) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        Twitt twitt = twittRepository.findByTwittId(twittId);
        if(user == null) throw new UserNotFoundException();
        if(twitt == null) throw new TwittNotFoundException();
        if(!user.getUuid().equals(twitt.getTwittUid())) throw new UserNotMatchException();

        TwittLike twittLike = twittLikeRespository.findByTwittIdAndUuid(twitt.getTwittId(), user.getUuid());
        List<Image> images = imageRepository.findByTwittId(twittId);

        if(twittLike != null) twittLikeRespository.delete(twittLike);
        for (Image i : images) imageRepository.delete(i);
        twittRepository.delete(twitt);
    }

    static TwittResponse getTwittInfo(User user, Twitt twitt, ImageRepository imageRepository, TwittLikeRespository twittLikeRespository, CommentRepository commentRepository) {
        List<String> imageList = new ArrayList<>();
        List<Image> images = imageRepository.findByTwittId(twitt.getTwittId());
        List<Comment> comments = commentRepository.findByCommentTwitIdOrderByCommentDateAsc(twitt.getTwittId());

        for(Image image : images)
            imageList.add(IMAGE_DIR + image.getImageName());

        return TwittResponse.builder()
                .twittId(twitt.getTwittId())
                .twittUid(twitt.getTwittUid())
                .twittContent(twitt.getTwittContent())
                .twittDate(twitt.getTwittDate())
                .twittImage(imageList)
                .comments(comments)
                .likeCount(twittLikeRespository.countAllByTwittId(twitt.getTwittId()))
                .isLike(twittLikeRespository.findByTwittIdAndUuid(twitt.getTwittId(), user.getUuid()) != null)
                .deleteAble(user.getUuid().equals(twitt.getTwittUid()))
                .build();
    }
}
