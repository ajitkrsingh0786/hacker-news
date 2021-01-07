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
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE LLL dd HH:mm:ss zzz yyyy",Locale.ROOT);
        if (date == null) {
            date = dateFormat.format(calendar.getTime());
        }

        Date newDate= dateFormat.parse(date);
        calendar.setTime(newDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String s = "" + DateFormat.getDateInstance().format(calendar.getTime());
        List<Post> posts = postRepository.findAllWithPublishedAtBefore(calendar.getTime());
        model.addAttribute("posts", posts);
        model.addAttribute("date",calendar.getTime());
        model.addAttribute("s", s);
        model.addAttribute("hidePosts",new ArrayList<Integer>());

        List<String> timeAgo = new ArrayList<>();
        for(Post post : posts){
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo",timeAgo);

        if(principal!= null){
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            model.addAttribute("hidePosts",hideRepository.findAllByUserId(user.getId()));
            model.addAttribute("userLikes",userLikes);
        }
    }

    @Override
    public void getAllBeforeMonth(String date, Model model, Principal principal) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        if (date == null) {
            date = ""+calendar.getTime();
        }
        DateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy",Locale.ROOT);
        Date newDate= format.parse(date);
        calendar.setTime(newDate);
        calendar.add(Calendar.MONTH, -1);
        String s = "" + DateFormat.getDateInstance().format(calendar.getTime());
        List<Post> posts = postRepository.findAllWithPublishedAtBefore(calendar.getTime());
        model.addAttribute("posts", posts);
        model.addAttribute("date",calendar.getTime());
        model.addAttribute("s", s);

        model.addAttribute("hidePosts",new ArrayList<Integer>());

        List<String> timeAgo = new ArrayList<>();
        for(Post post : posts){
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo",timeAgo);

        if(principal!= null){
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            model.addAttribute("hidePosts",hideRepository.findAllByUserId(user.getId()));
            model.addAttribute("userLikes",userLikes);
        }
    }

    @Override
    public void getAllBeforeYear(String date, Model model, Principal principal) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.ZONE_OFFSET);
        if (date == null) {
            date = ""+calendar.getTime();
        }
        DateFormat format = new SimpleDateFormat("EEE LLL dd HH:mm:ss Z yyyy",Locale.ROOT);
        Date newDate= format.parse(date);
        calendar.setTime(newDate);
        calendar.add(Calendar.YEAR, -1);
        String s = "" + DateFormat.getDateInstance().format(calendar.getTime());
        List<Post> posts = postRepository.findAllWithPublishedAtBefore(calendar.getTime());
        model.addAttribute("posts",posts);
        model.addAttribute("date",calendar.getTime());
        model.addAttribute("s", s);

        model.addAttribute("hidePosts",new ArrayList<Integer>());

        List<String> timeAgo = new ArrayList<>();
        for(Post post : posts){
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo",timeAgo);

        if(principal!= null){
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            model.addAttribute("hidePosts",hideRepository.findAllByUserId(user.getId()));
            model.addAttribute("userLikes",userLikes);
        }
    }
}