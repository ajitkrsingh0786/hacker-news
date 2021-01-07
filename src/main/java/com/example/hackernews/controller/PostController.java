package com.example.hackernews.controller;

import com.example.hackernews.entity.Post;
import com.example.hackernews.security.MyUserDetails;
import com.example.hackernews.services.service.LikeService;
import com.example.hackernews.services.secviceImp.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

@Controller
public class PostController {

    PostServiceImp postServiceImp;
    LikeService likeService;

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    @Autowired
    public void setPostService(PostServiceImp postServiceImp) {
        this.postServiceImp = postServiceImp;
    }

    @RequestMapping("/")
    public String showHomePage(Model model, Principal principal) {
        Date d = new Date(new Date().getTime());
        System.out.println(d);
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        System.out.println(""+calendar.getTime());
        return getAllPost(1, model, principal);
    }

    @RequestMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model, Principal principal) {
        postServiceImp.getAllPost(pageNo, model, principal);
        return "html/index";
    }

    @RequestMapping("/submit")
    public String showSubmitForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "html/submitForm";
    }

    @PostMapping("/submitPost")
    public String submitPost(@ModelAttribute Post post, @AuthenticationPrincipal MyUserDetails userDetails) {
        postServiceImp.addPost(post, userDetails);
        return "redirect:/";
    }

    @RequestMapping("/deletePost/{postId}")
    public String deletePostForm(@PathVariable(value = "postId") int postId, Model model) {
        model.addAttribute("post", postServiceImp.getPostById(postId));
        return  "html/deletePost";
    }

    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable(value = "postId") int postId, @Param(value = "delete") String delete) {
        if(delete.equals("Yes")){
            postServiceImp.deletePost(postId);
        }
        return  "redirect:/newest";
    }

    @GetMapping("/getPost")
    public String getPost(@RequestParam(name = "id") String id) {
        Post post = postServiceImp.getPost(id);
        return post.toString();
    }

    @GetMapping("AllPost/{pageNo}")
    public String getAllPost(@PathVariable(value = "pageNo") int pageNo, Model model, Principal principal) {
        postServiceImp.getAllPost(pageNo, model, principal);
        return "html/index";
    }

    @RequestMapping("/showFormForUpdate/{postId}")
    public String showFormForUpdate(@PathVariable(value = "postId") int postId, Model model) {
        Post post = postServiceImp.getPostById(postId);
        model.addAttribute("post", post);
        return "html/submitForm";
    }

    @RequestMapping("/item/{postId}")
    public String getPostById(@PathVariable(value = "postId") int postId, Model model) {
        model.addAttribute("post", postServiceImp.getPostById(postId));
        return "html/item";
    }

    @RequestMapping("/news")
    public String news(Model model, Principal principal) {
        return getAllPost(1, model, principal);
    }

    @RequestMapping("/newest")
    public String newest(Model model, Principal principal) {
        return getAllPost(1, model, principal);
    }

}
