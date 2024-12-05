package org.web.vikings_shop.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.entities.User;
import org.web.vikings_shop.repo.CategoryRepo;
import org.web.vikings_shop.repo.UserRepo;
import org.web.vikings_shop.service.CategoryService;
import org.web.vikings_shop.service.ProductService;
import org.web.vikings_shop.service.impl.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @RequestMapping("/home")
    public String userHome(Model model){
     List<Product> products =   productService.getProducts();
     model.addAttribute("products", products);
        return "user/userhome";
    }

    @RequestMapping("/category/{id}")
    public String userCategory(@PathVariable String id,Model model){
      Category category = categoryRepo.getById(id);
       List<Product> products = productService.getProductsByCategory(category);
       String name = category.getName();
       model.addAttribute("Cid", id);
       model.addAttribute("categoryName", name);
        model.addAttribute("products", products);

        return "/user/usercategory";
    }

    @RequestMapping("/profile")
    public String profile( Model model, HttpSession httpSession, Authentication authentication){

        String email = userService.getLoggedInUserEmail();
        System.out.println(email);
      Optional<User> user= userRepo.findByEmail(email);

        user.ifPresent(value -> model.addAttribute("user", value));


        return "user/userprofile";
    }
    @RequestMapping("/updateddetails")
    public String updateUserDetails(@ModelAttribute User userDetails){
        String email = userService.getLoggedInUserEmail();
        Optional<User> user= userRepo.findByEmail(email);
        System.out.println(userDetails.getEmail());
        User existingUser = user.get();
        System.out.println(existingUser.getEmail());
        existingUser.setName(userDetails.getName());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        userRepo.save(existingUser);


        return "redirect:/user/profile";

    }
    @RequestMapping("/order")
    public String userorder(){
        return "user/userorder";
    }
    @RequestMapping("/wishlist")
    public String userwishlist(){
        return "user/userwishlist";
    }
    @RequestMapping("/reviews")
    public String userreviews(){
        return "user/userreviews";
    }


}
