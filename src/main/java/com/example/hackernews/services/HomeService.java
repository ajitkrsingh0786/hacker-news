package com.example.hackernews.services;

import com.example.hackernews.entity.Comment;

import java.util.List;

public interface HomeService {
    List<Comment> findAllComment(String username);
}
