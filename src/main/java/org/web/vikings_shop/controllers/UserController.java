package org.web.vikings_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
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
    public String profile(@RequestParam String email, Model model){

      Optional<User> user= userRepo.findByEmail(email);

      model.addAttribute("user", user);
        return "user/userprofile";
    }


}
