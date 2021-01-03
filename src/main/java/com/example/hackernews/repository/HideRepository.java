package com.example.hackernews.repository;

import com.example.hackernews.entity.HidePost;
import com.example.hackernews.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HideRepository extends JpaRepository<HidePost,Long> {

    @Query("SELECT h FROM HidePost h WHERE h.postId=?1 AND h.userId=?2 ")
    HidePost findAllByPostIdAndUserId(int postId, int userId);

    @Query("SELECT DISTINCT h.postId FROM HidePost h where h.userId=?1 ")
    List<Integer> findAllByUserId(int id);

//    @Query("SELECT DISTINCT h.postId FROM HidePost h where h.userId=?1 ")
//    List<HidePost> findAllPostIdByUserId(int id);
}
