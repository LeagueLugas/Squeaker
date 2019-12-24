package com.squeaker.entry.controller;

import com.squeaker.entry.service.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeServiceImpl likeService;

    @PostMapping("/{twittId}")
    public void twittLike(@RequestHeader("Authorization") String token, @PathVariable Integer twittId) {
        likeService.twittLike(token, twittId);
    }

    @DeleteMapping("/{twittId}")
    public void twittUnLike(@RequestHeader("Authorization") String token, @PathVariable Integer twittId) {
        likeService.twittUnLike(token, twittId);
    }
}
