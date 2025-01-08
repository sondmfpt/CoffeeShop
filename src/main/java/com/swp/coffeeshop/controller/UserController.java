package com.swp.coffeeshop.controller;

import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.services.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/CoffeeShop/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/CoffeeShop/admin/users/" + user.getId();
    }

}
