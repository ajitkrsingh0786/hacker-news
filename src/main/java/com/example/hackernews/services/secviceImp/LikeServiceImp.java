package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.*;
import com.example.hackernews.repository.*;
import com.example.hackernews.services.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class LikeServiceImp implements LikeService {

    LikeRepository likeRepository;
    CommentLikeRepository commentLikeRepository;
    PostRepository postRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;

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

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void upVotePost(String postId, String userId) {
        Like like = new Like();
        Post post = postRepository.findById(Integer.valueOf(postId)).get();
        post.setPoint(post.getPoint()+1);
        like.setPostId(Integer.parseInt(postId));
        like.setUserId(Integer.valueOf(userId));
        likeRepository.save(like);
    }

    @Override
    public void downVotePost(String postId, String userId) {
        Post post = postRepository.findById(Integer.valueOf(postId)).get();
        post.setPoint(post.getPoint()-1);
        Like like=likeRepository.findAllByPostIdAndUserId(Integer.valueOf(postId),Integer.valueOf(userId));
        likeRepository.delete(like);
    }

    @Override
    public void upVoteComment(String commentId, Principal principal) {
        CommentLike commentLike =  new CommentLike();
        commentLike.setCommentId(commentRepository.findById(Integer.valueOf(commentId)).get());
        commentLike.setUserId(userRepository.findByUsername(principal.getName()).get());
        commentLikeRepository.save(commentLike);
    }

    @Override
    public void downVoteComment(String commentId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        Comment comment = commentRepository.findById(Integer.valueOf(commentId)).get();
        commentLikeRepository.delete(commentLikeRepository.findByUserIdAndCommentId(user,comment));
    }
}
