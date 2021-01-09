package com.example.hackernews.services.service;

import com.example.hackernews.entity.Post;
import com.example.hackernews.security.MyUserDetails;
import org.springframework.ui.Model;

import java.security.Principal;

public interface PostService {

    void addPost(Post post, MyUserDetails userDetails);
    void deletePost(int id);

    Post getPost(String id);

    void getAllPosts(int pageNo, Model model, Principal principal);

    void getAllPostDesc(int pageNo, Model model, Principal principal);

    void updatePost(String title, String postId);

    Post getPostById(int postId);

    Post getPostById(int postId, Principal principal,Model model);
}
