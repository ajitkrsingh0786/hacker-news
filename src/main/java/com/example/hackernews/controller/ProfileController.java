package com.example.hackernews.controller;

import com.example.hackernews.services.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    ProfileService profileService;

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping("/submissions")
    public String submissions(@RequestParam(name = "userId") String userId, Model model) {
        profileService.getAllSubmissions(userId, model);
        return "html/mySubmissions";
    }

    @RequestMapping("/upVotedSubmissions")
    public String upVotedSubmissions(@RequestParam(name = "userId") String userId, Model model) {
        profileService.getAllUpVotedSubmissions(userId, model);
        return "html/mySubmissions";
    }
}