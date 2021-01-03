package com.example.hackernews.services;

import com.example.hackernews.entity.Post;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.HideRepository;
import com.example.hackernews.repository.LikeRepository;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements PostServiceInterface {

    UserRepository userRepository;
    PostRepository postRepository;
    LikeRepository likeRepository;
    HideRepository hideRepository;

    @Autowired
    public void setHideRepository(HideRepository hideRepository) {
        this.hideRepository = hideRepository;
    }

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addPost(Post post, MyUserDetails userDetails) {
        if (post.getUser() == null && post.getCreatedAt()==null) {
            post.setUser(userRepository.findById(userDetails.getId()).get());
            post.setCreatedAt(new Date(new Date().getTime()));
        }

        if(post.getId() > 0){
            post.setCreatedAt(postRepository.findById(post.getId()).get().getCreatedAt());
        }
        post.setUpdatedAt(new Date(new Date().getTime()));
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
    public void getAllPost(int pageNo, Model model, Principal principal) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("createdAt").descending());
        Page<Post> pages = postRepository.findAll(pageable);

        model.addAttribute("posts", pages.getContent());
        model.addAttribute("isLast", pages.isLast());
        model.addAttribute("isFirst", pages.isFirst());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("hiddenPost",new ArrayList<Integer>());
        if(principal!= null){
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            System.out.println(hideRepository.findAllByUserId(user.getId()).size()+" =>LIKES");
            model.addAttribute("hidePosts",hideRepository.findAllByUserId(user.getId()));
            model.addAttribute("userLikes",userLikes);
        }
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
