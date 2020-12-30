package com.example.hackernews.controller;

import com.example.hackernews.entity.Post;
import com.example.hackernews.services.PostService;
import com.example.hackernews.services.PostServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class PostController {
     PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping("/")
     public String showHomePage(){
        return "html/index";
    }

    @PostMapping("submitPost")
    public String submitPost(@ModelAttribute Post post,
                             @RequestParam(name = "id") String userId){
        postService.addPost(post,userId);
        return "added";
    }

    @RequestMapping(name = "removePost",method = RequestMethod.DELETE)
    public String deletePost(@RequestParam(name = "id") String id){
        postService.deletePost(id);
        return "Post Deleted";
    }

    @GetMapping("/getPost")
    public String getPost(@RequestParam(name = "id") String id){
        Post post= postService.getPost(id);
        return post.toString();
    }

    @GetMapping("AllPost/{pageNo}")
    public List getAllPost(@PathVariable(name = "pageNo") String pageNo,
                           Model model){
        postService.getAllPost(pageNo,model);
        return (List) model.getAttribute("posts");
    }

    @PostMapping("/updatePost")
    public String updatePost(@RequestParam(name = "title") String title,
                             @RequestParam(name = "postId") String postId){
        postService.updatePost(title,postId);
        return "Post Updated";
    }

}
