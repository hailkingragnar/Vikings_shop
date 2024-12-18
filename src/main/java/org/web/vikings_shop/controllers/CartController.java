package org.web.vikings_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.web.vikings_shop.dto.CartItemDTO;
import org.web.vikings_shop.dto.ProductDTO;
import org.web.vikings_shop.dto.UserDTO;
import org.web.vikings_shop.entities.*;
import org.web.vikings_shop.form.UserForm;
import org.web.vikings_shop.form.UserOrder;
import org.web.vikings_shop.repo.CartItemRepo;
import org.web.vikings_shop.repo.CartRepo;
import org.web.vikings_shop.repo.ProductRepo;
import org.web.vikings_shop.repo.UserRepo;
import org.web.vikings_shop.service.CartService;
import org.web.vikings_shop.service.ProductService;
import org.web.vikings_shop.service.impl.UserService;

import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
   private ProductService productService;
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

    @Autowired
    private ProductRepo productRepo;
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
    @DeleteMapping("/delete/{cid}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> deleteProductFromCart(@PathVariable Integer cid) {

        Map<String, Object> response = new HashMap<>();
try{
        cartService.removeProductFromCart(cid);

        Optional<User> user = userRepo.findByEmail(userService.getLoggedInUserEmail());
        User userEntity = user.get();
        String userId = userEntity.getId();


        // Dynamically calculate the updated subtotal
        double updatedSubtotal = cartService.getUpdatedSubtotal(userId);
        double shipping = 10; // Example static shipping cost
        double updatedTotal = updatedSubtotal + shipping;

        // Populate the response map
        response.put("success", true);
        response.put("subtotal", updatedSubtotal);
        response.put("shipping", shipping);
        response.put("total", updatedTotal);

        return ResponseEntity.ok(response); // Return JSON response with HTTP 200 status
    } catch (Exception e) {
        // Handle errors
        response.put("success", false);
        response.put("message", "Failed to remove item from cart.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

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
        List<ProductDTO> products = productRepo.findProductsByCartId(cartId);



       double totalPrice = 0;
         for (ProductDTO product : products) {

          totalPrice += product.getQuantity() * product.getPrice();
          System.out.println(totalPrice);

         }
         model.addAttribute("totalprice", totalPrice);

        model.addAttribute("products", products);
        model.addAttribute("cartId" , cartId);

        System.out.println(cartId);
        return "user/usercart";
    }

    @PostMapping("/updatequantities/{cartId}")
    public ResponseEntity<String> updateCartQuantities(@RequestBody List<CartItemDTO> updatedQuantities , @PathVariable Integer cartId) {

try{


    productService.updateProductById(updatedQuantities,cartId);

    return ResponseEntity.ok("Quantities updated successfully!"); // Return JSON response with HTTP 200 status
    } catch (Exception e) {
        // Handle errors

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    }

    @RequestMapping("/checkout")
    public String Checkout( Model model) {
        Optional<User> user = userRepo.findByEmail(userService.getLoggedInUserEmail());
        User userEntity = user.get();
        String userid = userEntity.getId();
        Cart cart = cartRepo.findByUserId(userid);
        List<CartItem> cartItems = cartItemRepo.findByCartId(cart.getId());
         model.addAttribute("cartItems", cartItems);
         double totalPrice = 0;
         for(CartItem cartItem : cartItems) {
           double price =   cartItem.getProduct().getPrice();
           double quantity = cartItem.getQuantity();
           totalPrice += price*quantity;
         } model.addAttribute("totalprice", totalPrice);
        UserOrder userOrder = new UserOrder();
        model.addAttribute(userOrder);


    return "user/usercheckout";
    }

    @ResponseBody
    @RequestMapping("/savedetails")
    public String saveDetails(@ModelAttribute UserOrder userorder){

        Optional<User> user = userRepo.findByEmail(userService.getLoggedInUserEmail());
        User userEntity = user.get();
        Cart cart = userEntity.getCart();




        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setCity(userorder.getCity());
        shippingDetails.setCountry(userorder.getCountry());
        shippingDetails.setState(userorder.getState());
        shippingDetails.setDeliveryStatus("Successful");
        shippingDetails.setAddressLine1(userorder.getAddressLineOne());
        shippingDetails.setPhoneNumber(userorder.getPhone());
        shippingDetails.setId(UUID.randomUUID().toString());
        shippingDetails.setPostalCode(userorder.getZipCode());
        shippingDetails.setTrackingNumber("");
      //  shippingDetails.setDeliveryDate();
        shippingDetails.setRecipientName(userorder.getRecipientName());

        Orders order = new Orders();
        order.setId(UUID.randomUUID().toString());



        return "Successfully added";
    }



}
