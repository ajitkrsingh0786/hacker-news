package com.example.hackernews.services.service;

import java.security.Principal;

public interface LikeService {
    void upVotePost(String postId, String userId,Principal principal);

    void downVotePost(String postId, String userId);

    void upVoteComment(String commentId, Principal principal);

    void downVoteComment(String commentId, Principal principal);
}
