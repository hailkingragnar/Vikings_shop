package org.web.vikings_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.vikings_shop.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {
    boolean existsById(String id);


    Category getById(String id);
}
