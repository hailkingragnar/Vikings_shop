package org.web.vikings_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.form.AddNewProductForm;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/home")
    public String adminHome(){
        return "admin/adminhome";
    }

    @RequestMapping("addproduct")
    public String addProduct(Model model){
        AddNewProductForm addNewProductForm = new AddNewProductForm();
        model.addAttribute("addNewProductForm", addNewProductForm);
        return "admin/addproduct";
    }

    @RequestMapping("addnewproduct")
    public String addNewProduct(@ModelAttribute AddNewProductForm addNewProductForm){
        Product product = new Product();
        product.setName(addNewProductForm.getProductName());
        product.setPrice(addNewProductForm.getPrice());
        product.setDescription(addNewProductForm.getDescription());




        return "admin/addproduct";
    }
}
