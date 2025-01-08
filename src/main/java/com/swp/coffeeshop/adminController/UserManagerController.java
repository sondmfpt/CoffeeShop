package com.swp.coffeeshop.adminController;

import com.swp.coffeeshop.dto.UserNavigationRequest;
import com.swp.coffeeshop.models.Order;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.models.UserAddress;
import com.swp.coffeeshop.services.Address.AddressService;
import com.swp.coffeeshop.services.Order.OrderService;
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
    final private OrderService orderService;

    public UserManagerController(UserService userService, AddressService addressService, OrderService orderService) {
        this.userService = userService;
        this.addressService = addressService;
        this.orderService = orderService;
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
        List<Order> orders = orderService.getOrdersOfUser(id);
        user.setAddresses(userAddresses);
        user.setOrders(orders);
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        model.addAttribute("user", user);
        return "admin_userDetail";
    }

    //USER ADDRESS
    @DeleteMapping("/{id}/addresses/{addressId}")
    @ResponseBody
    public ResponseEntity<?> deleteAddress(@PathVariable("id") int id, @PathVariable("addressId") int addressId) {
        try {
            addressService.removeAddress(addressId);
            return ResponseEntity.ok("Delete Successfully!");
        } catch (Exception e) {
            return null;
        }

    }
}
