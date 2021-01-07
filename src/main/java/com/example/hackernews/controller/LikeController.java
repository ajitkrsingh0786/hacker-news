package com.example.hackernews.controller;

import com.example.hackernews.services.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LikeController {

    LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping("/upVotePost")
    public String upVotePost(@RequestParam(name = "postId") String postId,
                         @RequestParam(name = "userId",required = false,defaultValue = "") String userId,
                             @RequestParam(name = "goTo") String goTo,Principal principal){

        likeService.upVotePost(postId,userId,principal);
        return "redirect:"+goTo;
    }

    @GetMapping("/downVotePost")
    public String downVotePost(@RequestParam(name = "postId") String postId,
                               @RequestParam(name = "userId") String userId,
                               @RequestParam(name = "goTo") String goTo){
        likeService.downVotePost(postId,userId);
        return "redirect:"+goTo;
    }

    @GetMapping("/upVoteComment")
    public String upVoteComment(@RequestParam(name = "commentId") String commentId,
                                @RequestParam(name = "goTo") String goTo,
                                Principal principal){
        likeService.upVoteComment(commentId,principal);
        return "redirect:"+goTo;
    }

    @GetMapping("/downVoteComment")
    public String downVoteComment(@RequestParam(name = "commentId") String commentId,
                                  @RequestParam(name = "goTo") String goTo,
                                  Principal principal){
        likeService.downVoteComment(commentId,principal);
        return "redirect:"+goTo;
    }
}
