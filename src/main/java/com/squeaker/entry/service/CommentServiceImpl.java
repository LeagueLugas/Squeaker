package com.squeaker.entry.service;

import com.squeaker.entry.domain.entitys.Comment;
import com.squeaker.entry.domain.entitys.Twitt;
import com.squeaker.entry.domain.entitys.User;
import com.squeaker.entry.domain.repository.CommentRepository;
import com.squeaker.entry.domain.repository.TwittRepository;
import com.squeaker.entry.domain.repository.UserRepository;
import com.squeaker.entry.exception.CommentNotFoundException;
import com.squeaker.entry.exception.TwittNotFoundException;
import com.squeaker.entry.exception.UserNotFoundException;
import com.squeaker.entry.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    private UserRepository userRepository;
    private TwittRepository twittRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(UserRepository userRepository, TwittRepository twittRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.twittRepository = twittRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(String token, Integer twittId, String comment) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        Twitt twitt = twittRepository.findByTwittId(twittId);
        if(user == null) throw new UserNotFoundException();
        if(twitt == null) throw new TwittNotFoundException();

        commentRepository.save(
                Comment.builder()
                .commentUid(user.getUuid())
                .commentTwitId(twitt.getTwittId())
                .comment(comment)
                .commentDate(new Date().getTime())
                .build()
        );
    }

    @Override
    public void deleteComment(String token, Integer commentId) {
        User user = userRepository.findByUserId(JwtUtil.parseToken(token));
        Comment comment = commentRepository.findByCommentId(commentId);

        if(user == null) throw new UserNotFoundException();
        if(comment == null) throw new CommentNotFoundException();

        commentRepository.delete(comment);
    }
}