package com.example.hackernews.controller;

import com.example.hackernews.entity.User;
import com.example.hackernews.services.secviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    UserServiceImp userServiceImp;

    @Autowired
    public void setUserService(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "html/login";
    }


    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user,Model model) {
        return userServiceImp.addUser(user,model);
//        return "redirect:/";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "id") String id) {
        userServiceImp.deleteUser(id);
        return "deleted";
    }

    @RequestMapping("/changePasswordForm/{userId}")
    public String changePasswordForm(@PathVariable(value = "userId") int userId, Model model) {
        model.addAttribute("user", userServiceImp.getUserById(userId));
        return "html/changePassword";
    }

    @PostMapping("/changePassword/{userId}")
    public String changePassword(@RequestParam(name = "oldPassword") String oldPassword, @RequestParam(name =
            "newPassword") String newPassword, @PathVariable(value = "userId") int userId, Model model) {
        String successMessage = userServiceImp.changePassword(oldPassword, newPassword, userId);
        if (successMessage.equals("")) {
            return "redirect:/";
        }

        model.addAttribute("user", userServiceImp.getUserById(userId));
        model.addAttribute("successMessage", successMessage);
        return "html/changePassword";

    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {
        userServiceImp.updateUserDetails(user);
        return "redirect:/userProfile/" + user.getId();
    }

    @RequestMapping("/userProfile/{userId}")
    public String userProfile(Model model, @PathVariable(value = "userId") int userId, Principal principal) {
        System.out.println(userId);
        User user = userServiceImp.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("principal",principal);
        return "html/userProfile";
    }
}