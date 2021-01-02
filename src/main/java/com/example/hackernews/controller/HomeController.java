package com.example.hackernews.controller;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    HomeService homeService;

    @Autowired
    public void setHomeService(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "html/welcome";
    }


    @GetMapping("/thread")
    public String thread(@RequestParam(name = "username",defaultValue = "",required = false) String username,
                         Model model){
        List<Comment> userComment;

        if(!username.equals("")) {
            userComment = homeService.findAllComment(username);
        }else {
            userComment = new ArrayList<>();
        }
        model.addAttribute("userComment", userComment);
        return "html/thread";
    }
}
