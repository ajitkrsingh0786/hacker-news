package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.Post;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.HideRepository;
import com.example.hackernews.repository.LikeRepository;
import com.example.hackernews.repository.PostRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.services.service.FilterService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.text.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FilterServiceImp implements FilterService {

    PostRepository postRepository;
    UserRepository userRepository;
    HideRepository hideRepository;
    LikeRepository likeRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setHideRepository(HideRepository hideRepository) {
        this.hideRepository = hideRepository;
    }

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void getAllBeforeDay(String date, Model model, Principal principal) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE LLL dd HH:mm:ss zzz yyyy");
        if (date == null) {
            date = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(formatter);
        }
        System.out.println("AAA");
        System.out.println(date);
        ZonedDateTime newDate = ZonedDateTime.parse(date, formatter);
        newDate = newDate.minusDays(1);
        setDateRang(newDate,model,principal);
    }

    @Override
    public void getAllBeforeMonth(String date, Model model, Principal principal) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE LLL dd HH:mm:ss zzz yyyy");
        if (date == null) {
            date = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(formatter);
        }
        System.out.println("AAA");
        System.out.println(date);
        ZonedDateTime newDate = ZonedDateTime.parse(date, formatter);
        newDate = newDate.minusMonths(1);
        setDateRang(newDate,model,principal);
    }

    @Override
    public void getAllBeforeYear(String date, Model model, Principal principal) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE LLL dd HH:mm:ss zzz yyyy");
        if (date == null) {
            date = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(formatter);
        }
        System.out.println("AAA");
        System.out.println(date);
        ZonedDateTime newDate = ZonedDateTime.parse(date, formatter);
        newDate = newDate.minusYears(1);
        setDateRang(newDate,model,principal);
    }

    @Override
    public void getAllForwardDay(String date, Model model, Principal principal) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE LLL dd HH:mm:ss zzz yyyy");
        if (date == null) {
            date = LocalDateTime.now().atZone(ZoneId.systemDefault()).format(formatter);
        }
        System.out.println("AAA");
        System.out.println(date);
        ZonedDateTime newDate = ZonedDateTime.parse(date, formatter);
        newDate = newDate.plusDays(1);
        setDateRang(newDate,model,principal);
    }

    private void setDateRang(ZonedDateTime newDate,Model model, Principal principal ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE LLL dd HH:mm:ss zzz yyyy");
        Instant instant = newDate.toInstant();
        String s = "" + newDate.toLocalDate();
        List<Post> posts = postRepository.findAllWithPublishedAtBefore(Date.from(instant));
        model.addAttribute("posts", posts);
        model.addAttribute("date", newDate.format(formatter));
        model.addAttribute("s", s);
        model.addAttribute("hidePosts", new ArrayList<Integer>());

        List<String> timeAgo = new ArrayList<>();
        for (Post post : posts) {
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo", timeAgo);

        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            model.addAttribute("hidePosts", hideRepository.findAllByUserId(user.getId()));
            model.addAttribute("userLikes", userLikes);
        }

    }
}