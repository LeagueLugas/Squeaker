package com.squeaker.entry.domain.repository;

import com.squeaker.entry.domain.entitys.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByCommentTwitIdOrderByCommentDateAsc(Integer twittId);
    Comment findByCommentId(Integer commentId);
}
