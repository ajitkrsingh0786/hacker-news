package com.example.hackernews.services.service;

import org.springframework.ui.Model;

public interface ProfileService {
    void getAllSubmissions(String userId, Model model);

    void getAllUpVotedSubmissions(String userId, Model model);
}
