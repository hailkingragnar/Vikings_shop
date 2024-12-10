package org.web.vikings_shop.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web.vikings_shop.entities.Payment;

import java.security.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String id;  // Unique identifier for the transaction record

    private PaymentDTO payment;

    private double transactionAmount;  // The amount of money involved in this transaction

    private Timestamp transactionDate;  // Timestamp of the transaction

    private String transactionStatus;  // Status of the transaction (e.g., SUCCESS, FAILED)

}
