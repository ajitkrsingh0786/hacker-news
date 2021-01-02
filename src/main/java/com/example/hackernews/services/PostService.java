package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.PostRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.Optional;

@Service
public class PostService implements PostServiceInterface {

    UserRepository userRepository;
    PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addPost(Post post, MyUserDetails userDetails) {
        post.setUser(userRepository.findById(userDetails.getId()).get());
        post.setCreatedAt(new Date(new Date().getTime()));
        postRepository.save(post);
    }

    @Override
    public void deletePost(String id) {
        postRepository.delete(postRepository.getOne(Integer.valueOf(id)));
    }

    @Override
    public Post getPost(String id) {
        return postRepository.getOne(Integer.valueOf(id));
    }

    @Override
    public void getAllPost(int pageNo, Model model) {
        int pageSize = 10;

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("createdAt").descending());
        Page<Post> pages = postRepository.findAll(pageable);

        model.addAttribute("posts", pages.getContent());
        model.addAttribute("isLast", pages.isLast());
        model.addAttribute("isFirst", pages.isFirst());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pages.getTotalPages());
    }

    @Override
    public void updatePost(String title, String postId) {
        Post post = postRepository.getOne(Integer.valueOf(postId));
        post.setTitle(title);
        post.setUpdatedAt(new Date(new Date().getTime()));
        postRepository.save(post);
    }

    @Override
    public Post getPostById(int postId) {
        Optional<Post> optional = postRepository.findById(postId);
        return optional.orElse(null);
    }


}
