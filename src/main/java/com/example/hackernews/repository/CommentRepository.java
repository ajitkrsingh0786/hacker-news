package com.example.hackernews.repository;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByPost(Post post);

}

