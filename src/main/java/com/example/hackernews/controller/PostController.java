package com.example.hackernews.controller;

import com.example.hackernews.entity.Post;
import com.example.hackernews.services.PostService;
import com.example.hackernews.services.PostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController implements PostServiceInterface {

    PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("submitPost")
    public String submitPost(@ModelAttribute Post post,
                             @RequestParam(name = "id") String userId){
        postService.addPost(post,userId);
        return "added";
    }
}
