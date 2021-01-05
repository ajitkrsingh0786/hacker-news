package com.example.hackernews.services;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.CommentRepository;
import com.example.hackernews.repository.PostRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void saveComment(Comment comment, MyUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId()).get();
        comment.setUser(user);
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
        Optional<Comment> optional = commentRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveReplyComment(Comment comment, MyUserDetails userDetails, int parentCommentId) {
        Comment parentComment = commentRepository.findById(parentCommentId).get();
        comment.setPost(parentComment.getPost());
        comment.setCreatedAt(new Date(new Date().getTime()));
        comment.setUser(userRepository.findById(userDetails.getId()).get());
        comment.setParentComment(parentComment);
        Comment newComment =  commentRepository.save(comment);
        List<Comment> childList = parentComment.getChildComments();
        childList.add(newComment);
        parentComment.setChildComments(childList);
        commentRepository.save(parentComment);
    }
}
