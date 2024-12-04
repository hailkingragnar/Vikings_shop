package org.web.vikings_shop.dataloader;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.repo.CategoryRepo;

import java.util.UUID;

@Component
public class DataLoader {
    @Autowired
    private CategoryRepo categoryRepository;

    @PostConstruct
    public void loadData() {

            String electronicsUUID = "123e4567-e89b-12d3-a456-426614174000";
            addCategoryIfNotExists(electronicsUUID, "Electronics", "For Electronics");


            String clothingUUID = "223e4567-e89b-12d3-a456-426614174001";
            addCategoryIfNotExists(clothingUUID, "Fashion" , "For Clothing");

            String booksUUID = "323e4567-e89b-12d3-a456-426614174002";
            addCategoryIfNotExists(booksUUID, "Books", "For Books");

            String homeAppliancesUUID = "623i4569-e80b-02d3-a406-63535374003";
            addCategoryIfNotExists(homeAppliancesUUID, "HomeAppliances" , "For HomeAppliances");

             String groceriesUUID = "2348396e-e86b-12e3-b456-42661d4174003";
              addCategoryIfNotExists(groceriesUUID, "Groceries" , "For Groceries");

              String accessoriesUUID = "646747bhd-e99b-12d0-a356-363543d";
              addCategoryIfNotExists(accessoriesUUID, "Accessories" , "For Accessories");

        }

    private void addCategoryIfNotExists(String id, String categoryName, String description) {
        if (!categoryRepository.existsById(id)) {
            Category category = new Category();
            category.setCid(id);
            category.setName(categoryName);
            category.setDescription(description);
            categoryRepository.save(category);
        }
    }



}
