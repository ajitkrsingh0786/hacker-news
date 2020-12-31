package com.example.hackernews.repository;

import com.example.hackernews.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post , Integer> {

    @Query("SELECT p FROM Post p where p.url LIKE %?1% OR p.title LIKE %?1% " +
            "OR p.text LIKE %?1% OR p.user IN (SELECT u FROM User u where u.username LIKE %?1%)")
    List<Post> findSearch(String searchKeyword);
}
