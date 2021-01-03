package com.example.hackernews.services;

import com.example.hackernews.entity.Like;
import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.LikeRepository;
import com.example.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImp implements LikeService{

    LikeRepository likeRepository;
    PostRepository postRepository;

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
}
