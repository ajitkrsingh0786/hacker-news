package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.CommentLikeRepository;
import com.example.hackernews.repository.CommentRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.services.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class HomeServiceImp implements HomeService {

    CommentRepository commentRepository;
    UserRepository userRepository;
    CommentLikeRepository commentLikeRepository;

    @Autowired
    public void setCommentLikeRepository(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

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

    @Override
    public void findAllCommentByUsername(Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("user",user);
        List<Comment> userComments =user.getUserComments();
        Collections.reverse( userComments);
        model.addAttribute("userComments", userComments);
//        model.addAttribute("userComments",user.getUserComments().sort(new Comparator<Comment>() {
//            @Override
//            public int compare(Comment comment, Comment t1) {
//                return comment.getCreatedAt().compareTo(t1.getCreatedAt());
//            }
//        }));
        model.addAttribute("userLikedComment",commentLikeRepository.findAllByUserId(user));
        System.out.println(commentLikeRepository.findAllByUserId(user).size()+"hiiii");
        System.out.println(user.getUserComments().size());
    }
}
