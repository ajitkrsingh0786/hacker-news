package com.example.hackernews.controller;

import com.example.hackernews.services.service.HideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HideController {

    HideService hideService;

    @Autowired
    public void setHideService(HideService hideService) {
        this.hideService = hideService;
    }

    @RequestMapping("/hidePost")
    public String hidePost(@RequestParam(name = "postId") String postId, Principal principal, @RequestParam(name =
            "goTo") String goTo) {
        hideService.hidePost(postId, principal);
        return "redirect:" + goTo;
    }

    @RequestMapping("/unHidePost")
    public String unHidePost(@RequestParam(name = "postId") String postId, Principal principal, @RequestParam(name =
            "goTo") String goTo) {
        hideService.unHidePost(postId, principal);
        return "redirect:" + goTo;
    }

    @RequestMapping("/hidden")
    public String getHidden(Principal principal, Model model) {
        hideService.getHidden(principal, model);
        return "html/hiddenPost";
    }
}
