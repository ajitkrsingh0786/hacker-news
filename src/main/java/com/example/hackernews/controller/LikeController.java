package com.example.hackernews.controller;

import com.example.hackernews.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping("/upVotePost")
    public String upVotePost(@RequestParam(name = "postId") String postId,
                         @RequestParam(name = "userId") String userId){
        likeService.upVotePost(postId,userId);
        return "deleted";
    }

    @GetMapping("/downVotePost")
    public String downVotePost(@RequestParam(name = "postId") String postId,
                               @RequestParam(name = "userId") String userId){
        likeService.downVotePost(postId,userId);
        return "deleted";
    }
}
