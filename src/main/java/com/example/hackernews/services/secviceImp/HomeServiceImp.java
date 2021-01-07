package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.CommentLikeRepository;
import com.example.hackernews.repository.CommentRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.services.service.HomeService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

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
    public void findAllCommentByUsername(Principal principal, Model model,
                                         int pageNo) {
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("user",user);

        List<Comment> userComments =user.getUserComments();
        Collections.reverse( userComments);
        PagedListHolder<Comment> userCommentsPage = new PagedListHolder<>(userComments);
        userCommentsPage.setPageSize(10);
        userCommentsPage.setPage(pageNo-1);

        List<String> timeAgo = new ArrayList<>();
        for(Comment comment : userCommentsPage.getPageList()){
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(comment.getCreatedAt()));
        }

        model.addAttribute("timeAgo",timeAgo);
        model.addAttribute("isLast",userCommentsPage.isLastPage());
        model.addAttribute("currentPage",userCommentsPage.getPage()+1);
        model.addAttribute("userComments", userCommentsPage.getPageList());

//        model.addAttribute("userLikedComment",commentLikeRepository.findAllByUserId(user));
    }
}
