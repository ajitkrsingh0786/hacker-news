package com.example.hackernews.services.service;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import com.example.hackernews.entity.User;
import com.example.hackernews.security.MyUserDetails;

import java.util.List;

public interface CommentService {

    void saveComment(Comment comment, MyUserDetails userDetails);

    List<Comment> getCommentsByPost(Post post);
    List<Comment> getAllComments();

    void deleteCommentById(int id);

    Comment getCommentById(int id);
    List<Comment> getCommentByUser(User user);

    void saveReplyComment(Comment comment, MyUserDetails userDetails, int parentCommentId);
}
