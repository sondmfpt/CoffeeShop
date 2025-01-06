package com.swp.coffeeshop.controller;

import com.swp.coffeeshop.models.Cart;
import com.swp.coffeeshop.models.GuestUser;
import com.swp.coffeeshop.models.User;
import com.swp.coffeeshop.services.Cart.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/CoffeeShop")
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestBody Map<String, Object> productOrder, HttpServletRequest request) {
        var productId = Integer.parseInt(productOrder.get("productId").toString());
        Map<String, Object> attributes = (Map<String, Object>) productOrder.get("attributes");
        var quantity = Integer.parseInt(productOrder.get("quantity").toString());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        GuestUser guest = (GuestUser) session.getAttribute("guest");
        if (user != null) {
            cartService.addProductToCart(user, productId, attributes, quantity);
        } else if (guest != null) {
            cartService.addProductToCart(guest, productId, attributes, quantity);
        } else {
            return "redirect:/CoffeeShop/login";
        }
        CommonController.updateCartSize(session);
        return "redirect:/CoffeeShop/product/" + productOrder.get("productId");
    }

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Cart> carts = null;
        if (user != null) {
            carts = cartService.getAllCartByUserId(user.getId());
        } else {
            GuestUser guest = (GuestUser) session.getAttribute("guest");
            carts = cartService.getAllCartByTrackingId(guest.getTrackingId());
        }
        model.addAttribute("carts", carts);
        return "cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeCart(@PathVariable("id") int id, HttpSession session) {
        cartService.removeCart(id);
        CommonController.updateCartSize(session);
        return "redirect:/CoffeeShop/cart";
    }

    @PostMapping("/cart/update-quantity")
    public String updateQuantity(@RequestBody Map<String, Object> cartUpdate) {
        Integer cartId = Integer.parseInt(cartUpdate.get("cartId").toString());
        Integer quantity = Integer.parseInt(cartUpdate.get("quantity").toString());
        cartService.updateQuantity(cartId, quantity);
        return "redirect:/CoffeeShop/cart";
    }

}
