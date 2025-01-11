package com.swp.coffeeshop.controller;

import com.swp.coffeeshop.dto.UserUpdateRequest;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.services.Email.EmailService;
import com.swp.coffeeshop.services.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/CoffeeShop/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
    }

    @PostMapping("/{id}/update")
    @ResponseBody
    public String update(@RequestBody UserUpdateRequest user) {
        user.setId(user.getId());
        if (user.isSendForUser()) EmailService.changeProfile(user);
        return userService.updateUser(user);
    }

}
