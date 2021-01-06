package com.example.hackernews.controller;

import com.example.hackernews.services.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LikeController {

    LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping("/upVotePost")
    public String upVotePost(@RequestParam(name = "postId") String postId,
                         @RequestParam(name = "userId") String userId,
                             @RequestParam(name = "goTo") String goTo){
        likeService.upVotePost(postId,userId);
        return "redirect:"+goTo;
    }

    @GetMapping("/downVotePost")
    public String downVotePost(@RequestParam(name = "postId") String postId,
                               @RequestParam(name = "userId") String userId,
                               @RequestParam(name = "goTo") String goTo){
        likeService.downVotePost(postId,userId);
        return "redirect:"+goTo;
    }
}
