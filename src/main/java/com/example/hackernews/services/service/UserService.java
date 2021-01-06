package com.example.hackernews.services.service;

import com.example.hackernews.entity.User;
import org.springframework.ui.Model;

public interface UserService {

    void deleteUser(String id);

    String changePassword(String oldPassword, String newPassword,int userId);

    void updateUserDetails(User user);

    User getUserById(int userId);

    public String addUser(User user, Model model);

}