package com.example.hackernews.controller;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import com.example.hackernews.security.MyUserDetails;
import com.example.hackernews.services.CommentService;
import com.example.hackernews.services.PostService;
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

    PostService postService;
    @Autowired
    CommentService commentService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

   @RequestMapping("/addCommentForm/{postId}")
    public String addCommentForm(@PathVariable(value = "postId") int postId, Model model) {
        Comment comment = new Comment();
        Post post = postService.getPostById(postId);
        comment.setPost(post);
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
        Post post = postService.getPostById(postId);

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
    public String replyComment(@PathVariable(value = "commentId") int commentId, Model model){
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
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
