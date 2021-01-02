package com.example.hackernews.services;

import com.example.hackernews.entity.User;
import com.example.hackernews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService implements UserServiceInterface{

    final String USER_ROLE="ROLE_USER";
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        user.setRole(USER_ROLE);
        user.setCreatedAt(new Date(new Date().getTime()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.delete(userRepository.getOne(Integer.valueOf(id)));
    }

    @Override
    public String changePassword(String oldPassword, String newPassword,String id) {
        User user = userRepository.getOne(Integer.valueOf(id));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(encoder.matches(oldPassword, user.getPassword())){
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
            return "Password Changed";
        }else {
            return "Password Not Matches";
        }
    }

    public String updateUserDetails(String username, String about, String email) {
        if(userRepository.findByEmail(email).isPresent()){
            return "Email ID Already Present";
        }else {
            User userToUpdate = userRepository.findByUsername(username).get();
            userToUpdate.setEmail(email);
            userToUpdate.setAbout(about);
            userRepository.save(userToUpdate);
            return "Details Saved";
        }

    }
}
