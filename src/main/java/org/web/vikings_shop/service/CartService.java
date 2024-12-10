package org.web.vikings_shop.service;

import org.web.vikings_shop.entities.Cart;

public interface CartService {
    Cart addProductToCart(String productId);
    Cart removeProductFromCart(Integer cartItemId);
    Cart getCart();
    Double getUpdatedSubtotal(String UserId);

}
