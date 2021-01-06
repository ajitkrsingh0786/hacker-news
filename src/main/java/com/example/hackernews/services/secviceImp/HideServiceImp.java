package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.HidePost;
import com.example.hackernews.entity.Post;
import com.example.hackernews.entity.User;
import com.example.hackernews.repository.HideRepository;
import com.example.hackernews.repository.LikeRepository;
import com.example.hackernews.repository.PostRepository;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.services.service.HideService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class HideServiceImp implements HideService {

    UserRepository userRepository;
    HideRepository hideRepository;
    LikeRepository likeRepository;
    PostRepository postRepository;

    @Autowired
    public void setLikeRepository(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setHideRepository(HideRepository hideRepository) {
        this.hideRepository = hideRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void hidePost(String postId, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            HidePost hidePost = new HidePost();
            hidePost.setPostId(Integer.valueOf(postId));
            hidePost.setUserId(user.getId());
            hideRepository.save(hidePost);
        }
    }

    @Override
    public void unHidePost(String postId, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            HidePost hidePost = hideRepository.findAllByPostIdAndUserId(
                    Integer.valueOf(postId), user.getId());
            hideRepository.delete(hidePost);
        }
    }

    @Override
    public void getHidden(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByUsername(username).get();
            List<Integer> hidePosts = hideRepository.findAllByUserId(user.getId());
            List<Post> posts = postRepository.findPostByPostId(hidePosts);
            System.out.println(posts.size() + " ==> postId Size");
//            List<Integer> userLikes = likeRepository.findAllByUserId(user.getId());
            List<String> timeAgo = new ArrayList<>();
            for(Post post : posts){
                PrettyTime prettyTime = new PrettyTime();
                timeAgo.add(prettyTime.format(post.getCreatedAt()));
            }
            model.addAttribute("timeAgo",timeAgo);
            model.addAttribute("userLikes",likeRepository.findAllByUserId(user.getId()));
            model.addAttribute("posts", posts);
        }
    }
}
