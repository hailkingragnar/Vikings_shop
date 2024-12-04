package org.web.vikings_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.form.AddNewProductForm;
import org.web.vikings_shop.repo.CategoryRepo;
import org.web.vikings_shop.repo.ProductRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;

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

    @RequestMapping("/addnewproduct")
    public String addNewProduct(@ModelAttribute AddNewProductForm addNewProductForm, @RequestParam MultipartFile image) throws IOException {
        Product product = new Product();
        product.setName(addNewProductForm.getProductName());
        product.setPrice(addNewProductForm.getPrice());
        product.setDescription(addNewProductForm.getDescription());
        product.setQuantity(addNewProductForm.getQuantity());
        String categoryID = addNewProductForm.getCategory();
        Category category =   categoryRepo.getById(categoryID);
        System.out.println("The category is: " + category);
        System.out.println("The category ID is: " + categoryID);
        product.setPid(UUID.randomUUID().toString());

        product.setCategory(category);


        if (!image.isEmpty()) {
            String imageName = UUID.randomUUID()+ "_" + image.getOriginalFilename();
            Path uploadDir = Paths.get("src/main/resources/static/images/product");
            Files.createDirectories(uploadDir); // Ensure directory exists

            Path imagePath = uploadDir.resolve(imageName);

            System.out.println("The image path is: " + imagePath);
            Files.write(imagePath, image.getBytes()); // Save the file
            product.setImage(imageName); // Save the image name to the product
        }
            productRepo.save(product);

        return "redirect:/admin/addproduct";
    }
}
