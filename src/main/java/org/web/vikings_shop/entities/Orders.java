package org.web.vikings_shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.Map;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp orderDate;
    private String status;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    private ShippingDetails shippingDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )

    private Set<Product> products;

    @OneToMany(mappedBy = "order")
    private Set<Payment> payments;


    @ElementCollection
    @CollectionTable(
            name = "order_product_quantities", // Table to store the quantities of each product
            joinColumns = @JoinColumn(name = "order_id") // Foreign key to the order
    )
    @MapKeyJoinColumn(name = "product_id") // Foreign key to the product
    @Column(name = "quantity") // Column to store the quantity of the product
    private Map<Product, Integer> productQuantities; // Map of product and its quantity

}
