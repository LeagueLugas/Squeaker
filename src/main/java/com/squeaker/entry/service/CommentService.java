package com.squeaker.entry.service;

import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    void addComment(String token, Integer twittId, String comment);
    void deleteComment(String token, Integer commentId);
}
