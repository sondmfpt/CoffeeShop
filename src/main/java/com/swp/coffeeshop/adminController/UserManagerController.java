package com.swp.coffeeshop.adminController;

import com.swp.coffeeshop.dto.UserNavigationRequest;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.models.UserAddress;
import com.swp.coffeeshop.services.Address.AddressService;
import com.swp.coffeeshop.services.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/CoffeeShop/admin/users")
public class UserManagerController {

    final private UserService userService;
    final private AddressService addressService;

    public UserManagerController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping("")
    public String userList(Model model) {
        List<User> allUsers = userService.findAll();
        Map numOfUser = userService.numberOfUsersByRole();
        model.addAttribute("users", allUsers);
        model.addAttribute("numOfUser", numOfUser);
        return "admin_userList";
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

    //    USER DETAIL
    @GetMapping("/{id}")
    public String userDetail(@PathVariable("id") int id, Model model) {
        User user = userService.findById(id);
        List<UserAddress> userAddresses = addressService.getAllAddressByUserId(id);
        user.setAddresses(userAddresses);
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("user", user);
        return "admin_userDetail";
    }
}
