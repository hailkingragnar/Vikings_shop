package org.web.vikings_shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.repo.ProductRepo;
import org.web.vikings_shop.service.AddNewProduct;

@Service
public class AddNewProductImpl implements AddNewProduct {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product save(Product product) {



        return productRepo.save(product);
    }
}
