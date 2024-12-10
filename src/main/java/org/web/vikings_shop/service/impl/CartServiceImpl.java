package org.web.vikings_shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.vikings_shop.entities.Cart;
import org.web.vikings_shop.entities.CartItem;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.entities.User;
import org.web.vikings_shop.repo.CartItemRepo;
import org.web.vikings_shop.repo.CartRepo;
import org.web.vikings_shop.repo.ProductRepo;
import org.web.vikings_shop.repo.UserRepo;
import org.web.vikings_shop.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;


    @Override
    public Cart addProductToCart(String productId) {
        // Retrieve the logged-in user
        Optional<User> user = userRepo.findByEmail(userService.getLoggedInUserEmail());
        User userEntity = user.orElseThrow(() -> new IllegalArgumentException("User not found"));

        System.out.println("Inside User");

        // Get or create the user's cart
        Cart cart = cartRepo.findByUser(userEntity)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userEntity);
                    userEntity.setCart(newCart); // Link the cart to the user
                    return cartRepo.save(newCart); // Save the new cart to the database
                });
        System.out.println("Inside Cart");

        // Retrieve the product
        Product product = productRepo.findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        System.out.println("Inside Product");

        CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);


        System.out.println("Inside CartItem");

        if (cartItem != null) {
            // Update the quantity if the product is already in the cart
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepo.save(cartItem);
        } else {
            // Add a new item to the cart
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(1);
            newCartItem.setUser(userEntity);
            newCartItem.setCart(cart);
            cartItemRepo.save(newCartItem);
            cart.getItems().add(newCartItem);
        }

        // Reduce the product quantity in stock
        if (product.getQuantity() <= 0) {
            throw new IllegalArgumentException("Product out of stock");
        }
        product.setQuantity(product.getQuantity() - 1);
        productRepo.save(product);

        return cartRepo.save(cart); // Save and return the updated cart
    }

    @Override
    public Cart removeProductFromCart(Integer cartItemId) {
        // Retrieve the cart item
        System.out.println("Inside Cart");
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
         System.out.println("Inside CartItem");
        // Retrieve the product and restore stock
        Product product = cartItem.getProduct();
        System.out.println("Inside Product");

        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepo.save(product);
        System.out.println("Saved Product");

        // Remove the item from the cart and delete it
        Cart cart = cartItem.getCart();
        System.out.println("Inside Cart");
        cart.getItems().removeIf(item -> item.getId() == cartItemId);
        System.out.println("Removed Product");
        cartItemRepo.delete(cartItem);
         System.out.println("Removed CartItem");

        return cartRepo.save(cart);
        // Save and return the updated cart
    }

    @Override
    public Cart getCart() {
        // Retrieve the logged-in user
        Optional<User> user = userRepo.findByEmail(userService.getLoggedInUserEmail());
        User userEntity = user.orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Retrieve the user's cart
        return cartRepo.findByUserId(userEntity.getId());
    }

    @Override
    public Double getUpdatedSubtotal(String UserId) {
        Cart cart = cartRepo.findByUserId(UserId);
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()) // Multiply price by quantity
                .sum(); // Sum up all item totals
    }
    }

    // Method to calculate the updated subtotal for the user's cart
