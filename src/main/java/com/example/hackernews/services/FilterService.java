package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import org.springframework.ui.Model;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface FilterService {

    void getAllBeforeDay(String date, Model model) throws ParseException;
    void getAllBeforeMonth(String date, Model model) throws ParseException;
    void getAllBeforeYear(String date, Model model) throws ParseException;
}
