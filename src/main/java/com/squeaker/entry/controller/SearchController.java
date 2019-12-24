package com.squeaker.entry.controller;

import com.squeaker.entry.domain.payload.response.SearchUserResponse;
import com.squeaker.entry.service.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchServiceImpl searchService;

    @GetMapping
    public List<SearchUserResponse> searchUser(@RequestHeader("Authorization") String token, @RequestParam String userName) {
        return searchService.searchUser(token, userName);
    }
}
