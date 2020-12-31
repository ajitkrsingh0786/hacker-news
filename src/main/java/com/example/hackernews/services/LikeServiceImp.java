package com.example.hackernews.services;

import com.example.hackernews.entity.Like;
import com.example.hackernews.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImp implements LikeService{

    LikeRepository likeRepository;

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void upVotePost(String postId, String userId) {
        Like like = new Like();
        like.setPostId(Integer.parseInt(postId));
        like.setUserId(Integer.valueOf(userId));
        likeRepository.save(like);
    }

    @Override
    public void downVotePost(String postId, String userId) {
        Like like=likeRepository.findAllByPostIdAndUserId(Integer.valueOf(postId),Integer.valueOf(userId));
        likeRepository.delete(like);
    }
}
