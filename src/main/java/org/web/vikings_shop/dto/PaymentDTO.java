package org.web.vikings_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String id;

    private double amount;  // Amount paid

    private String paymentMethod;  // Payment method (e.g., Credit Card, PayPal, Bank Transfer)

    private String paymentStatus;  // Status of the payment (e.g., SUCCESS, PENDING, FAILED)

    private Timestamp paymentDate;  // Timestamp of when the payment was made

    private String transId;  // A unique transaction identifier from the payment gateway

    private String paymentGateway;

    private OrdersDTO orders;

    private TransactionDTO transaction;

}
