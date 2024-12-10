package org.web.vikings_shop.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.web.vikings_shop.dto.ProductDTO;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, String> {

    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE p.pid = :productId")
    Product findProductById(@Param("productId") String productId);

    @Query("SELECT new org.web.vikings_shop.dto.ProductDTO(p.pid ,p.name, p.description, p.price, ci.quantity, p.image, ci.id) " +
            "FROM Product p JOIN CartItem ci ON ci.product = p " +
            "WHERE ci.cart.id = :cartId")
    List<ProductDTO> findProductsByCartId(@Param("cartId") Integer cartId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p.quantity FROM Product p where p.pid= :productId")
    int findQuantityByProductId(String productId);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = :newProductStock WHERE p.pid = :productId")
    void updateQuantityByProductId(String productId, int newProductStock);
}
