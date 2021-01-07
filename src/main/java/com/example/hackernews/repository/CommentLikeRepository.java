package com.example.hackernews.repository;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.CommentLike;
import com.example.hackernews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike,Integer> {

    CommentLike findByUserIdAndCommentId(User user, Comment comment);

    @Query("SELECT cl.commentId.id FROM CommentLike cl WHERE cl.userId=?1 ")
    List<Integer> findAllByUserId(User user);
}
