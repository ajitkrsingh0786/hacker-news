package com.example.hackernews.services;

public interface LikeService {
    void upVotePost(String postId, String userId);

    void downVotePost(String postId, String userId);
}
