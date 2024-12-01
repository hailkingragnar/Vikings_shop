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
public class Transactions {
    @Id
    private String id;  // Unique identifier for the transaction record

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")  // Reference to the associated payment
    private Payment payment;

    private double transactionAmount;  // The amount of money involved in this transaction

    private Timestamp transactionDate;  // Timestamp of the transaction

    private String transactionStatus;  // Status of the transaction (e.g., SUCCESS, FAILED)

}
