package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.Twitt;
import com.squeaker.entry.domain.entitys.TwittLike;
import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.repository.TwittLikeRespository;
import com.squeaker.entry.domain.repository.TwittRepository;
import com.squeaker.entry.domain.repository.UserRepository;
import com.squeaker.entry.exception.AlreadyLikeException;
import com.squeaker.entry.exception.AlreadyUnLikeException;
import com.squeaker.entry.exception.TwittNotFoundException;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private UserRepository userRepository;
    private TwittRepository twittRepository;
    private TwittLikeRespository twittLikeRespository;

    public LikeServiceImpl(UserRepository userRepository, TwittRepository twittRepository, TwittLikeRespository twittLikeRespository) {
        this.userRepository = userRepository;
        this.twittRepository = twittRepository;
        this.twittLikeRespository = twittLikeRespository;
    }

    @Override
    public void twittLike(String token, Integer twittId) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        Twitt twitt = twittRepository.findByTwittId(twittId);

        if(user == null) throw new UserNotFoundException();
        if(twitt == null) throw new TwittNotFoundException();

        TwittLike twittLike = twittLikeRespository.findByTwittId(twitt.getTwittId());
        if(twittLike != null) throw new AlreadyLikeException();

        twittLikeRespository.save(
                TwittLike.builder()
                        .twittId(twitt.getTwittId())
                        .uuid(user.getUuid())
                        .build()
        );
    }

    @Override
    public void twittUnLike(String token, Integer twittId) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        Twitt twitt = twittRepository.findByTwittId(twittId);

        if(user == null) throw new UserNotFoundException();
        if(twitt == null) throw new TwittNotFoundException();

        TwittLike twittLike = twittLikeRespository.findByTwittId(twitt.getTwittId());
        if(twittLike == null) throw new AlreadyUnLikeException();

        twittLikeRespository.delete(twittLike);

    }
}
