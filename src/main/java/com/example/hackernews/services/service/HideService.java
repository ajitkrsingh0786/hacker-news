package com.example.hackernews.services.service;

import org.springframework.ui.Model;

import java.security.Principal;

public interface HideService {
    
    void hidePost(String postId, Principal principal);

    void unHidePost(String postId, Principal principal);

    void getHidden(Principal principal, Model model);
}
