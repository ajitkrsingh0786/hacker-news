package com.example.hackernews.controller;

import com.example.hackernews.services.FilterService;
import com.example.hackernews.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FilterController {

    @Autowired
    FilterService filterService;

    @RequestMapping("/pastPosts")
    public String pastDayPosts(@Param("date") String date , Model model) throws ParseException {
        filterService.getAllBeforeDay(date, model);
        return "html/past";
    }

    @RequestMapping("/pastMonthPosts")
    public String pastMonthPosts(@Param("date") String date , Model model) throws ParseException {
        filterService.getAllBeforeMonth(date, model);
        return "html/past";
    }

    @RequestMapping("/pastYearPosts")
    public String pastYearPosts(@Param("date") String date , Model model) throws ParseException {
        filterService.getAllBeforeYear(date, model);
        return "html/past";
    }

}
