package com.squeaker.entry.service;

import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void twittLike(String token, Integer twittId);
    void twittUnLike(String token, Integer twittId);
}
