package com.squeaker.entry.service;

import org.springframework.stereotype.Service;

@Service
public interface FollowService {
    void follow(String token, Integer uuid);
    void unFollow(String token, Integer uuid);
}
