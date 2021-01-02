package com.example.hackernews.services;

public interface UserServiceInterface {

    void deleteUser(String id);

    String changePassword(String oldPassword, String newPassword,String id);

    public String updateUserDetails(String username, String about, String email);
}
