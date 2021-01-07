package com.example.hackernews.controller;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.User;
import com.example.hackernews.services.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
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
    public String thread(Principal principal,
                         Model model){

        if(principal != null){
            homeService.findAllCommentByUsername(principal,model);
        }else{
            model.addAttribute("user",new User());

            model.addAttribute("userLikedComment",new ArrayList<Integer>());
        }

//        model.addAttribute("userComment", new ArrayList<Comment>());
        return "html/thread";
    }

}
