package com.example.hackernews.controller;

import com.example.hackernews.services.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.text.ParseException;

@Controller
public class FilterController {

    @Autowired
    FilterService filterService;

    @RequestMapping("/pastPosts")
    public String pastDayPosts(@Param("date") String date , Model model, Principal principal) throws ParseException {
        filterService.getAllBeforeDay(date, model,principal);
        return "html/past";
    }

    @RequestMapping("/pastMonthPosts")
    public String pastMonthPosts(@Param("date") String date , Model model, Principal principal) throws ParseException {
        filterService.getAllBeforeMonth(date, model,principal);
        return "html/past";
    }

    @RequestMapping("/pastYearPosts")
    public String pastYearPosts(@Param("date") String date , Model model, Principal principal) throws ParseException {
        filterService.getAllBeforeYear(date, model,principal);
        return "html/past";
    }

    @RequestMapping("/forwardPosts")
    public String forwardDayPosts(@Param("date") String date , Model model, Principal principal) throws ParseException {
        filterService.getAllForwardDay(date, model,principal);
        return "html/past";
    }
}
