package com.squeaker.entry.controller;

import com.squeaker.entry.service.FollowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowServiceImpl followService;

    @PostMapping("/{uuid}")
    public void follow(@RequestHeader("Authorization") String token, @PathVariable Integer uuid) {
        followService.follow(token, uuid);
    }

    @DeleteMapping("/{uuid}")
    public void unFollow(@RequestHeader("Authorization") String token, @PathVariable Integer uuid) {
        followService.unFollow(token, uuid);
    }
}
