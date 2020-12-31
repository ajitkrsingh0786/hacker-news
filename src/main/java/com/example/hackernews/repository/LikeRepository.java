package com.example.hackernews.repository;

import com.example.hackernews.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    @Query("SELECT l FROM Like l WHERE l.postId=?1 AND l.userId=?2 ")
    Like findAllByPostIdAndUserId(int postId, int userId);
}
