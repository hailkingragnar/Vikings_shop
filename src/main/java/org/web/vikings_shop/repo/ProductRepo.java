package org.web.vikings_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.vikings_shop.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

}
