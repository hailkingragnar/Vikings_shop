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
public class Payment {
    @Id
    private String id;  // Unique identifier for the payment

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")  // Reference to the associated order
    private Orders order;

    @OneToOne(fetch = FetchType.LAZY)
    private Transactions transaction;

    private double amount;  // Amount paid

    private String paymentMethod;  // Payment method (e.g., Credit Card, PayPal, Bank Transfer)

    private String paymentStatus;  // Status of the payment (e.g., SUCCESS, PENDING, FAILED)

    private Timestamp paymentDate;  // Timestamp of when the payment was made

    private String transId;  // A unique transaction identifier from the payment gateway

    private String paymentGateway;  // Name of the payment gateway (e.g., Stripe, PayPal)

}
