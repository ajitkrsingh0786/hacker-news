package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import org.springframework.ui.Model;

public interface PostServiceInterface{

    void deletePost(String id);

    Post getPost(String id);

    void getAllPost(String pageNo, Model model);

    void updatePost(String title, String postId);
}
