package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.LikeRepository;
import com.example.hackernews.repository.PostRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImp implements ProfileService {

    LikeRepository likeRepository;
    PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void getAllSubmissions(String userId, Model model) {

//        List<Integer> postIds =likeRepository.findAllByUserId(Integer.valueOf(userId));
        List<Post> posts = postRepository.findAllByUserId(Integer.valueOf(userId));
        model.addAttribute("posts", posts);

        List<String> timeAgo = new ArrayList<>();
        for (Post post : posts) {
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo", timeAgo);
    }

    @Override
    public void getAllUpVotedSubmissions(String userId, Model model) {

        List<Integer> postIds = likeRepository.findAllByUserId(Integer.valueOf(userId));
        List<Post> posts = postRepository.findPostByPostId(postIds);
        model.addAttribute("posts", posts);

        List<String> timeAgo = new ArrayList<>();
        for (Post post : posts) {
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo", timeAgo);
    }


}
