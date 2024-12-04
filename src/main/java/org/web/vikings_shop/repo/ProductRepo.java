package org.web.vikings_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, String> {

    List<Product> findByCategory(Category category);
}
