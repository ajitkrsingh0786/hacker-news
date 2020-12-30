package com.example.hackernews.services;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;

import java.util.List;

public interface CommentService {

    void saveComment(Comment comment);

    List<Comment> getCommentsByPost(Post post);

    void deleteCommentById(int id);

    Comment getCommentById(int id);
}
