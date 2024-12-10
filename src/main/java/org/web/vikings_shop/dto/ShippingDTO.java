package org.web.vikings_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web.vikings_shop.entities.Orders;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDTO {

    private String id;

    private OrdersDTO order;

    private String recipientName;  // Name of the person receiving the order

    private String addressLine1;  // First line of the shipping address
    private String city;          // City of the recipient
    private String state;         // State or region of the recipient
    private String postalCode;    // Postal or ZIP code of the recipient
    private String country;       // Country of the recipient

    private String phoneNumber;   // Contact number of the recipient

    private String deliveryStatus;  // Status of shipping (e.g., PENDING, SHIPPED, DELIVERED, CANCELLED)

    private Timestamp deliveryDate;  // Date when the order was delivered (if applicable)

    private String trackingNumber;
}
