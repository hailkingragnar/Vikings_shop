package org.web.vikings_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.web.vikings_shop.dto.CartItemDTO;
import org.web.vikings_shop.dto.ProductDTO;
import org.web.vikings_shop.entities.CartItem;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.pid = :productId")
    CartItem findByCartIdAndProductId(@Param("cartId") Integer cartId, @Param("productId") String productId);

    @Query("SELECT ci.id FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.pid = :productId")
    List<CartItemDTO> findCidByCartIdAndProductId(@Param("cartId") Integer cartId , @Param("productId") String productId);

    List<CartItem> findByCartId(Integer cartId);

    @Modifying
    @Query("UPDATE CartItem c SET c.quantity = :quantity WHERE c.id = :cartItemId")
    void updateQuantityById(int cartItemId, int quantity);

    @Query("SELECT c.product.pid FROM CartItem c WHERE c.id = :cartItemId")
    String findProductIdByCartItemId(@Param("cartItemId") Integer cartItemId);

    @Query("SELECT ci.quantity FROM CartItem ci WHERE ci.id = :id")
    int getQuantityById(int id);

    @Query("SELECT ci.id FROM CartItem ci WHERE ci.cart.id= :cartId")
    Integer getCartItemId(Integer cartId);

    @Query("SELECT (ci.id , ci.quantity, ci.product.quantity, ci.product.pid) FROM CartItem ci WHERE ci.id= :id")
    CartItemDTO getCartItemByIdForUpdate(Integer id);
}
