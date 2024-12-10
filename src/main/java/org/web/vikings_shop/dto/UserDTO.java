package org.web.vikings_shop.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web.vikings_shop.entities.Cart;
import org.web.vikings_shop.entities.CartItem;
import org.web.vikings_shop.entities.Orders;
import org.web.vikings_shop.entities.WishList;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gender;
    private CartDTO cart;
    private List<WishListDTO> wishlist;
    private List<CartItemDTO> cartitem;
    private List<OrdersDTO> order;
}
