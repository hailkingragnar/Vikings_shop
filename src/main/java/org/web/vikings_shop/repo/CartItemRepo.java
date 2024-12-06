package org.web.vikings_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.web.vikings_shop.entities.CartItem;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.pid = :productId")
    CartItem findByCartIdAndProductId(@Param("cartId") Integer cartId, @Param("productId") String productId);

    List<CartItem> findByCartId(Integer cartId);
}
