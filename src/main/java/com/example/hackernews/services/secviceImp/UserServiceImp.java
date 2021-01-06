package com.example.hackernews.services.secviceImp;

import com.example.hackernews.entity.User;
import com.example.hackernews.repository.UserRepository;
import com.example.hackernews.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;
    final String USER_ROLE = "ROLE_USER";


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String addUser(User user,Model model) {
        if (!userRepository.findByUsername(user.getUsername()).isPresent()){
            user.setRole(USER_ROLE);
            user.setCreatedAt(new Date(new Date().getTime()));
            //  user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
            return "redirect:/";
        }else{
            model.addAttribute("user",user);
            model.addAttribute("isPresent",true);
            return "html/login";
        }
    }

    @Override
    public void deleteUser(String id) {
        userRepository.delete(userRepository.getOne(Integer.valueOf(id)));
    }

    @Override
    public String changePassword(String oldPassword, String newPassword, int userId) {
        User user = userRepository.findById(userId).get();
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (oldPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return "";
        }
        return "Current password incorrect. Please try again.";
    }

    @Override
    public void updateUserDetails(User user) {
        User preUser = userRepository.findById(user.getId()).get();
        preUser.setEmail(user.getEmail());
        preUser.setAbout(user.getAbout());
        userRepository.save(preUser);
    }

    @Override
    public User getUserById(int userId) {
        Optional<User> optional = userRepository.findById(userId);
        return optional.orElse(null);
    }
}