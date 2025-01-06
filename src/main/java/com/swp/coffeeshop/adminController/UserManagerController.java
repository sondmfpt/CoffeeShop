package com.swp.coffeeshop.adminController;

import com.swp.coffeeshop.dto.UserNavigationRequest;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.services.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/CoffeeShop/admin/users")
public class UserManagerController {

    UserService userService;

    public UserManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String userList(Model model) {
        List<User> allUsers = userService.findAll();
        Map numOfUser = userService.numberOfUsersByRole();
        model.addAttribute("users", allUsers);
        model.addAttribute("numOfUser", numOfUser);
        return "admin_userManager";
    }

    @PostMapping("/navigation")
    @ResponseBody
    public ResponseEntity<?> getUserNavigation(@RequestBody UserNavigationRequest request) {
        try {
            return ResponseEntity.ok(userService.getUsersNavigation(request));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
