package com.example.hackernews.services;

import com.example.hackernews.entity.User;

public interface UserServiceInterface {

    void deleteUser(String id);

    String changePassword(String oldPassword, String newPassword,int userId);

    void updateUserDetails(User user);

    User getUserById(int userId);

}
