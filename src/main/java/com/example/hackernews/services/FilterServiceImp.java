package com.example.hackernews.services;

import com.example.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class FilterServiceImp implements FilterService {

    @Autowired
    PostRepository postRepository;

    @Override
    public void getAllBeforeDay(String date, Model model) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        if (date == null) {
            date = ""+calendar.getTime();
        }
        DateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy", Locale.ENGLISH);
        Date newDate= format.parse(date);
        calendar.setTime(newDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String s = "" + DateFormat.getDateInstance().format(calendar.getTime());
        model.addAttribute("posts", postRepository.findAllWithPublishedAtBefore(calendar.getTime()));
        model.addAttribute("date",calendar.getTime());
        model.addAttribute("s", s);
    }

    @Override
    public void getAllBeforeMonth(String date, Model model) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        if (date == null) {
            date = ""+calendar.getTime();
        }
        DateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy");
        Date newDate= format.parse(date);
        calendar.setTime(newDate);
        calendar.add(Calendar.MONTH, -1);
        String s = "" + DateFormat.getDateInstance().format(calendar.getTime());
        model.addAttribute("posts", postRepository.findAllWithPublishedAtBefore(calendar.getTime()));
        model.addAttribute("date",calendar.getTime());
        model.addAttribute("s", s);
    }

    @Override
    public void getAllBeforeYear(String date, Model model) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        if (date == null) {
            date = ""+calendar.getTime();
        }
        DateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy");
        Date newDate= format.parse(date);
        calendar.setTime(newDate);
        calendar.add(Calendar.YEAR, -1);
        String s = "" + DateFormat.getDateInstance().format(calendar.getTime());
        model.addAttribute("posts", postRepository.findAllWithPublishedAtBefore(calendar.getTime()));
        model.addAttribute("date",calendar.getTime());
        model.addAttribute("s", s);

    }
}