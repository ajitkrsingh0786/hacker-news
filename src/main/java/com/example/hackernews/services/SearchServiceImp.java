package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import com.example.hackernews.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class SearchServiceImp implements SearchService{

    PostRepository postRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> searchResult(String searchKeyword, Model model) {
        List<Post> posts = postRepository.findSearch(searchKeyword);
        return posts;
    }
}
