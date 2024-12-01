package org.web.vikings_shop.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
    public class ShippingDetails {

        @Id
        private String id;  // Unique identifier for the shipping details

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "order_id", nullable = false)  // Reference to the associated order
        private Orders order;

        private String recipientName;  // Name of the person receiving the order

        private String addressLine1;  // First line of the shipping address
        private String city;          // City of the recipient
        private String state;         // State or region of the recipient
        private String postalCode;    // Postal or ZIP code of the recipient
        private String country;       // Country of the recipient

        private String phoneNumber;   // Contact number of the recipient

        private String deliveryStatus;  // Status of shipping (e.g., PENDING, SHIPPED, DELIVERED, CANCELLED)

        private Timestamp deliveryDate;  // Date when the order was delivered (if applicable)

        private String trackingNumber;   // Tracking number provided by the shipping carrier


        // Getters and setters...
    }


