package com.example.hackernews.controller;

import com.example.hackernews.entity.User;
import com.example.hackernews.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "html/login";
    }


    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);

        return "redirect:/";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "id") String id) {
        userService.deleteUser(id);
        return "deleted";
    }

    @RequestMapping("/changePasswordForm/{userId}")
    public String changePasswordForm(@PathVariable(value = "userId") int userId, Model model) {
        model.addAttribute("user", userService.getUserById(userId));
        return "html/changePassword";
    }

    @PostMapping("/changePassword/{userId}")
    public String changePassword(@RequestParam(name = "oldPassword") String oldPassword, @RequestParam(name =
            "newPassword") String newPassword, @PathVariable(value = "userId") int userId, Model model) {
        String successMessage = userService.changePassword(oldPassword, newPassword, userId);
        if(successMessage.equals("")){
          return  "redirect:/";
        }

        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("successMessage", successMessage);
        return "html/changePassword";

    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user) {
        userService.updateUserDetails(user);
        return "redirect:/userProfile/" + user.getId();
    }

    @RequestMapping("/userProfile/{userId}")
    public String userProfile(Model model, @PathVariable(value = "userId") int userId) {
        System.out.println(userId);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "html/userProfile";
    }

}
