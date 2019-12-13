package com.squeaker.entry.controller;

import com.squeaker.entry.service.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @PostMapping("/{twittId}")
    public void addComment(@RequestHeader("Authorization") String token,
                           @PathVariable Integer twittId, @RequestParam String comment) {

        commentService.addComment(token, twittId, comment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@RequestHeader("Authorization") String token, @PathVariable Integer commentId) {
        commentService.deleteComment(token, commentId);
    }
}
