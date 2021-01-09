package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.Comment;
import com.example.hackernews.entity.Post;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.*;
import com.example.hackernews.security.MyUserDetails;
import com.example.hackernews.services.service.PostService;
import org.ocpsoft.prettytime.PrettyTime;
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
public class PostServiceImp implements PostService {

    UserRepository userRepository;
    PostRepository postRepository;
    LikeRepository likeRepository;
    HideRepository hideRepository;
    CommentLikeRepository commentLikeRepository;

    @Autowired
    public void setCommentLikeRepository(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

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
    public void deletePost(int id) {
         postRepository.deleteById(id);
    }

    @Override
    public Post getPost(String id) {
        return postRepository.getOne(Integer.valueOf(id));
    }

    @Override
    public void getAllPosts(int pageNo, Model model, Principal principal) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Post> pages = postRepository.findAll(pageable);
        model.addAttribute("posts", pages.getContent());
        model.addAttribute("isLast", pages.isLast());
        model.addAttribute("isFirst", pages.isFirst());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("hidePosts",new ArrayList<Integer>());

        List<String> timeAgo = new ArrayList<>();
        for(Post post : pages.getContent()){
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo",timeAgo);
        if(principal!= null){
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            model.addAttribute("hidePosts",hideRepository.findAllByUserId(user.getId()));
            model.addAttribute("userLikes",userLikes);
        }
    }

    @Override
    public void getAllPostDesc(int pageNo, Model model, Principal principal) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("createdAt").descending());
        Page<Post> pages = postRepository.findAll(pageable);

        model.addAttribute("posts", pages.getContent());
        model.addAttribute("isLast", pages.isLast());
        model.addAttribute("isFirst", pages.isFirst());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("hidePosts",new ArrayList<Integer>());

        List<String> timeAgo = new ArrayList<>();
        for(Post post : pages.getContent()){
            PrettyTime prettyTime = new PrettyTime();
            timeAgo.add(prettyTime.format(post.getCreatedAt()));
        }
        model.addAttribute("timeAgo",timeAgo);
        if(principal!= null){
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
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
        Post post = optional.get();

        return optional.orElse(null);
    }

    @Override
    public Post getPostById(int postId, Principal principal,Model model) {
        Optional<Post> optional = postRepository.findById(postId);
        Post post = optional.get();
        List<Integer> likeCommentsId = new ArrayList<>();

        model.addAttribute("postIsLike",false);

        if(principal != null){
            User user = userRepository.findByUsername(principal.getName()).get();
            likeCommentsId = commentLikeRepository.findAllByUserId(user);
            if(likeRepository.findAllByPostIdAndUserId(postId,user.getId()) != null){
                model.addAttribute("postIsLike",true);
            }
        }

        model.addAttribute("likeCommentsId",likeCommentsId);
        System.out.println(likeCommentsId.size()+"==>SIZE");
        return optional.orElse(null);
    }
}
