package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import com.example.hackernews.security.MyUserDetails;
import org.springframework.ui.Model;

public interface PostServiceInterface{

    void addPost(Post post, MyUserDetails userDetails);
    void deletePost(String id);

    Post getPost(String id);

    void getAllPost(int pageNo, Model model);

    void updatePost(String title, String postId);

    Post getPostById(int postId);
}
