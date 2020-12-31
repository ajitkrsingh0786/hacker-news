package com.example.hackernews.services;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
        comment.setCreatedAt(new Date(new Date().getTime()));
         commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        return null;
    }

    @Override
    public void deleteCommentById(int id) {

    }

    @Override
    public Comment getCommentById(int id) {
        return null;
    }
}
