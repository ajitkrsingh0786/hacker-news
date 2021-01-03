package com.example.hackernews.services;

import com.example.hackernews.entity.User;
import com.example.hackernews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    UserRepository userRepository;
    final String USER_ROLE="ROLE_USER";


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        user.setRole(USER_ROLE);
        user.setCreatedAt(new Date(new Date().getTime()));
      //  user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.delete(userRepository.getOne(Integer.valueOf(id)));
    }

    @Override
    public String changePassword(String oldPassword, String newPassword,int userId) {
        User user = userRepository.findById(userId).get();
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(oldPassword.equals(user.getPassword())){
            user.setPassword(newPassword);
            userRepository.save(user);
            return "";
        }
        return "Current password incorrect. Please try again.";
    }

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
