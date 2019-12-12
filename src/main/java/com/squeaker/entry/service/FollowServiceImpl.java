package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.Follow;
import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.repository.FollowRepository;
import com.squeaker.entry.domain.repository.UserRepository;
import com.squeaker.entry.exception.AlreadyFollowException;
import com.squeaker.entry.exception.AlreadyUnFollowException;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    private UserRepository userRepository;
    private FollowRepository followRepository;

    public FollowServiceImpl(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    @Override
    public void follow(String token, Integer uuid) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        User target = userRepository.findByUuid(uuid);

        if(user == null || target == null) throw new UserNotFoundException();

        Follow follow = followRepository.findByFollowerAndFollowing(user.getUuid(), target.getUuid());
        if(follow != null) throw new AlreadyFollowException();

        followRepository.save(
            Follow.builder()
                .follower(user.getUuid())
                .following(target.getUuid())
                .build()
        );
    }

    @Override
    public void unFollow(String token, Integer uuid) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        User target = userRepository.findByUuid(uuid);

        if(user == null || target == null) throw new UserNotFoundException();

        Follow follow = followRepository.findByFollowerAndFollowing(user.getUuid(), target.getUuid());
        if(follow == null) throw new AlreadyUnFollowException();

        followRepository.delete(follow);
    }
}
