package com.example.hackernews.services.service;

import com.example.hackernews.entity.Comment;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

public interface HomeService {
    List<Comment> findAllComment(String username);

    void findAllCommentByUsername(Principal principal, Model model);
}
