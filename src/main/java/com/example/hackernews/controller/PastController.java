package com.example.hackernews.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class PastController {


    @RequestMapping("/pastPosts")
    public String pastPosts(@Param("date") Date date) {
        System.out.println(date);
        return "html/past";
    }

}
