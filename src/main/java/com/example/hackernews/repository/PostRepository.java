package com.example.hackernews.repository;

import com.example.hackernews.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post , Integer> {

    @Query("SELECT p FROM Post p where p.url LIKE %?1% OR p.title LIKE %?1% " +
            "OR p.text LIKE %?1% OR p.user IN (SELECT u FROM User u where u.username LIKE %?1%)")
    List<Post> findSearch(String searchKeyword);

    @Query("select post from Post post where post.createdAt <= ?1")
    List<Post> findAllWithPublishedAtBefore(Date createdAt);

    @Query(value = "SELECT * FROM posts WHERE id in :postsId ", nativeQuery = true)
    List<Post> findPostByPostId(@Param("postsId") List<Integer> postsId);

    List<Post> findAllByUserId(Integer valueOf);
}
