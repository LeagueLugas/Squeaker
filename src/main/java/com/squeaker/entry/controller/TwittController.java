package com.squeaker.entry.controller;

import com.squeaker.entry.domain.payload.response.TwittResponse;
import com.squeaker.entry.service.TwittServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/twitt")
public class TwittController {

    @Autowired
    TwittServiceImpl twittService;

    @GetMapping
    public List<TwittResponse> getTwitts(@RequestHeader("Authorization") String token, @RequestParam String count) {
        return twittService.getTwitts(token, Integer.parseInt(count));
    }

    @GetMapping("/{twiitId}")
    public TwittResponse getTwitt(@RequestHeader("Authorization") String token, @PathVariable Integer twiitId) {
        return twittService.getTwitt(token, twiitId);
    }

    @PostMapping
    public void addTwitt(@RequestHeader("Authorization") String token, @RequestParam String content,
                         @RequestParam(value = "files", required = false) MultipartFile[] files) {
        twittService.addTwitt(token, content, files);
    }

    @DeleteMapping("/{TwittId}")
    public void deleteTwitt(@RequestHeader("Authorization") String token, @PathVariable Integer TwittId) {
        twittService.deleteTwit(token, TwittId);
    }

}
