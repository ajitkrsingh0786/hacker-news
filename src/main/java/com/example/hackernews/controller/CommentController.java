package com.example.hackernews.controller;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.CommentLikeRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.security.MyUserDetails;
import com.example.hackernews.services.service.CommentService;
import com.example.hackernews.services.secviceImp.PostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class CommentController {

    PostServiceImp postServiceImp;
    CommentLikeRepository commentLikeRepository;
    CommentService commentService;
    UserRepository userRepository;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPostServiceImp(PostServiceImp postServiceImp) {
        this.postServiceImp = postServiceImp;
    }

    @Autowired
    public void setCommentLikeRepository(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    @RequestMapping("/addCommentForm/{postId}")
    public String addCommentForm(@PathVariable(value = "postId") int postId, Model model, Principal principal) {
        Comment comment = new Comment();
        Post post = postServiceImp.getPostById(postId,principal,model);
        comment.setPost(post);
//        for()
        model.addAttribute("comment", comment);
        return "html/commentForm";
    }

    @PostMapping("/addComment")
    public String addComment(@ModelAttribute("comment") Comment comment, @AuthenticationPrincipal MyUserDetails userDetails) {
        commentService.saveComment(comment, userDetails);
        return "redirect:/addCommentForm/" + comment.getPost().getId();
    }

    @RequestMapping("/viewComments/{postId}")
    public String viewComments(@PathVariable(value = "postId") int postId, Model model) {
        Post post = postServiceImp.getPostById(postId);

        model.addAttribute("comments", commentService.getCommentsByPost(post));
        model.addAttribute("postId", postId);
        return "html/viewComments";
    }

    @RequestMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable(value = "id") int id) {
        commentService.deleteCommentById(id);
        return "redirect:/";
    }

    @RequestMapping("/updateComment/{commentId}/{postId}")
    public String updateComment(@PathVariable(value = "commentId") int commentId,
                                @PathVariable(value = "postId") int postId, Model model) {
        model.addAttribute("commentObj", commentService.getCommentById(commentId));
        model.addAttribute("postId", postId);
        return "html/addComment";
    }

    @RequestMapping("/replyComment/{commentId}")
    public String replyComment(@PathVariable(value = "commentId") int commentId, Model model,Principal principal){
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        if(principal != null){
            model.addAttribute("likeCommentsId",commentLikeRepository.findAllByUserId(userRepository.findByUsername(principal.getName()).get()));
        }
        model.addAttribute("parentComment", commentService.getCommentById(commentId));
        return "html/replyComment";
    }

    @PostMapping("/addReply/{parentCommentId}")
    public String addReply(@PathVariable(value = "parentCommentId") int parentCommentId, @ModelAttribute Comment comment,@AuthenticationPrincipal MyUserDetails userDetails){
        commentService.saveReplyComment(comment,userDetails,parentCommentId);
        Comment comment1 = commentService.getCommentById(parentCommentId);
        return "redirect:/addCommentForm/"+comment1.getPost().getId();
    }

}
