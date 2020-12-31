package com.example.hackernews.controller;

import com.example.hackernews.entity.User;
import com.example.hackernews.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "html/login";
    }


    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user){
        userService.addUser(user);

        return "redirect:/";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "id") String id){
        userService.deleteUser(id);
        return "deleted";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "id" ) String id){
        return userService.changePassword(oldPassword,newPassword,id);
    }

}
