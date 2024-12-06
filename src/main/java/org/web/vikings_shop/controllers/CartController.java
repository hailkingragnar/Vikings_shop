package org.web.vikings_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.web.vikings_shop.entities.Cart;
import org.web.vikings_shop.entities.CartItem;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.entities.User;
import org.web.vikings_shop.repo.CartItemRepo;
import org.web.vikings_shop.repo.CartRepo;
import org.web.vikings_shop.repo.UserRepo;
import org.web.vikings_shop.service.CartService;
import org.web.vikings_shop.service.impl.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepo cartRepo;

    /**
     * Add a product to the cart.
     */
    @PostMapping("/add")
    @ResponseBody
    public String addProductToCart(@RequestParam("productId") String productId) {
        cartService.addProductToCart(productId);
        return "Product added to cart!";
    }

    /**
     * Delete a product from the cart.
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteProductFromCart(@RequestParam Integer cartItemId) {
        cartService.removeProductFromCart(cartItemId);
        return "Product removed from cart!";
    }

    /**
     * View the cart.
     */
    @GetMapping("/view")
    public String viewCart(Model model) {
        Optional<User> user = userRepo.findByEmail(userService.getLoggedInUserEmail());
        User userEntity = user.get();
        Optional<Cart> cart = cartRepo.findByUser(userEntity);
        Cart cartEntity = cart.get();
        Integer cartId = cartEntity.getId();
        List<CartItem> cartItem = cartItemRepo.findByCartId(cartId);



        List<Product> products = new ArrayList<>();
        for (CartItem item : cartItem) {
            System.out.println(item);
            products.add(item.getProduct());
        }



        //model.addAttribute("products", products);
        return "/user/usercart";
    }
}
