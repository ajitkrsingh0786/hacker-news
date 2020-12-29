package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.PostRepository;
import com.example.hackernews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Service
public class PostService  implements PostServiceInterface{

    UserRepository userRepository;
    PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addPost(Post post, String userId) {
        System.out.println(post.getURL());
        System.out.println(post.getText());
        System.out.println(post.getURL());
        post.setCreatedAt(new Date(new Date().getTime()));
        post.setUser(userRepository.getOne(Integer.valueOf(userId)));
        postRepository.save(post);
    }
}
