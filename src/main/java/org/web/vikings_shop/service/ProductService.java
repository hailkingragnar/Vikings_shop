package org.web.vikings_shop.service;

import org.web.vikings_shop.dto.CartItemDTO;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;

import java.util.List;

public interface ProductService {

   List<Product> getProducts();

   List<Product> getProductsByCategory(Category category);

   void updateProductById(List<CartItemDTO> updatedQuantities,int cartId);
}
