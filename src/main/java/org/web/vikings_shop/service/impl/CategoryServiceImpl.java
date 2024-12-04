package org.web.vikings_shop.service.impl;

import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.repo.CategoryRepo;
import org.web.vikings_shop.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> getCategories() {
        List<Category> category = categoryRepo.findAll();
        System.out.println(category);
        return category;
    }
}
