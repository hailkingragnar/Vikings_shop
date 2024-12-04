package org.web.vikings_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.repo.CategoryRepo;
import org.web.vikings_shop.service.CategoryService;
import org.web.vikings_shop.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepo categoryRepo;

    @RequestMapping("/home")
    public String userHome(Model model){
     List<Product> products =   productService.getProducts();
     model.addAttribute("products", products);
        return "user/userhome";
    }

    @RequestMapping("/category")
    public String userCategory(Model model){
      String  id = "646747bhd-e99b-12d0-a356-363543d";
      Category category = categoryRepo.getById(id);
       List<Product> products = productService.getProductsByCategory(category);

       for(Product product : products){
           System.out.println(product);


       } return "/user/userhome";
    }


}
