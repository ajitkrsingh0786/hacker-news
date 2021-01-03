package com.example.hackernews.controller;

import com.example.hackernews.entity.Post;
import com.example.hackernews.security.MyUserDetails;
import com.example.hackernews.services.LikeService;
import com.example.hackernews.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {

     PostService postService;
     LikeService likeService;

     @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping("/")
     public String showHomePage(Model model, Principal principal){
        if(principal != null) {
            System.out.println(principal.getName());
        }
        return getAllPost(1, model,principal);

    }

    @RequestMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model,Principal principal) {
        postService.getAllPost(pageNo,model,principal);
        return "html/index";
    }


    @RequestMapping("/submit")
    public String showSubmitForm( Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        return "html/submitForm";
    }

    @PostMapping("/submitPost")
    public String submitPost(@ModelAttribute Post post, @AuthenticationPrincipal MyUserDetails userDetails){
        postService.addPost(post, userDetails);
        return "redirect:/";
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
    public String getAllPost(@PathVariable(value = "pageNo") int pageNo, Model model,Principal principal){
        postService.getAllPost(pageNo,model,principal);
        return "html/index";
    }

    @RequestMapping("/showFormForUpdate/{postId}")
    public String showFormForUpdate(@PathVariable(value = "postId") int postId, Model model) {
        Post post = postService.getPostById(postId);
        model.addAttribute("post", post);
        return "html/submitForm";
    }

    @RequestMapping("/item/{postId}")
    public String getPostById(@PathVariable(value = "postId") int postId, Model model) {
            model.addAttribute("post",postService.getPostById(postId));
            return "html/item";
    }

}
