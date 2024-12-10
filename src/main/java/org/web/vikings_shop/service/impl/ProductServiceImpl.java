package org.web.vikings_shop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.vikings_shop.dto.CartItemDTO;
import org.web.vikings_shop.dto.ProductDTO;
import org.web.vikings_shop.entities.CartItem;
import org.web.vikings_shop.entities.Category;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.repo.CartItemRepo;
import org.web.vikings_shop.repo.ProductRepo;
import org.web.vikings_shop.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepo.findByCategory(category);
    }

    @Override
    @Transactional
    public void updateProductById(List<CartItemDTO> updatedQuantities,int cartId) {

        for (CartItemDTO cartItemDTO : updatedQuantities) {
            int cartItemId = cartItemDTO.getId();
            System.out.println(cartItemId);
            int updatedQuantity = cartItemDTO.getQuantity();
            System.out.println(updatedQuantity);

            List<CartItem> cartItem = cartItemRepo.findByCartId(cartId);
            for(CartItem cartItem1 : cartItem) {
                int cartItemId1 = cartItem1.getId();
                System.out.println(cartItemId1);
                int cartItemQuantity = cartItem1.getQuantity();
                System.out.println(cartItemQuantity);
                int productQuantity = cartItem1.getProduct().getQuantity();
                System.out.println(productQuantity);
                String productId = cartItem1.getProduct().getPid();
                System.out.println(productId);
                int stockChange = updatedQuantity - cartItemQuantity;
                System.out.println(stockChange);
                if (stockChange > 0 && productQuantity < stockChange) {
                    throw new IllegalStateException("Insufficient stock for product ID: " + productId);
                }
                    cartItemRepo.updateQuantityById(cartItemId1, updatedQuantity);
                    // Update product stock
                    int newProductStock = productQuantity - stockChange;
                    System.out.println(newProductStock);
                    productRepo.updateQuantityByProductId(productId, newProductStock);
                }

            }

         //   CartItemDTO cartItemDTONew = cartItemRepo.getCartItemByIdForUpdate(cartItemId);
           // int oldQuantity = cartItemDTONew.getQuantity();
            //System.out.println(oldQuantity);
            //int currentProductStock = cartItemDTONew.getProductQuantity();
            //System.out.println(currentProductStock);
            //String productId = cartItemDTONew.getProductId();
            //System.out.println(productId);

            // Calculate the stock change
        //    int stockChange = updatedQuantity - oldQuantity;

            // Validate stock availability
        //    if (stockChange > 0 && currentProductStock < stockChange) {
          //      throw new IllegalStateException("Insufficient stock for product ID: " + productId);
            //}


            // Update cart item quantity
          //  cartItemRepo.updateQuantityById(cartItemId, updatedQuantity);
            // Update product stock
            //int newProductStock = currentProductStock - stockChange;
            //productRepo.updateQuantityByProductId(productId, newProductStock);

        }

    }

