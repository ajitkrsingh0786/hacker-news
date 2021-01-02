package com.example.hackernews.services;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.CommentRepository;
import com.example.hackernews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImp implements HomeService{

    CommentRepository commentRepository;
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> findAllComment(String username) {
        User user= userRepository.findByUsername(username).get();
        List<Comment> userComment = commentRepository.findByUser(user);
        return userComment;
    }
}
