package org.web.vikings_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String pid;
    private String name;
    private double price;
    private int quantity;
    private String description;


    @ManyToOne
    @JoinColumn(name = "cid", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY)
    private Set<WishList> wishlists;

    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY)
    private Set<Cart> cart;

    @ManyToMany(mappedBy = "products") // This defines the inverse side of the relationship
    private Set<Orders> orders;
}
