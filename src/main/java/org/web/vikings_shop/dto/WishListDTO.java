package org.web.vikings_shop.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web.vikings_shop.entities.Product;
import org.web.vikings_shop.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListDTO {
    private String id;
    private UserDTO user;
    private ProductDTO product;
}
