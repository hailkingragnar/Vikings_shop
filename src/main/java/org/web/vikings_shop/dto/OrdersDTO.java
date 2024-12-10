package org.web.vikings_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web.vikings_shop.entities.Payment;

import java.security.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private String order_id;
    private Timestamp orderDate;
    private String status;
    private double totalAmount;
    private String shippingAddress;
    private String paymentMethod;
    private List<UserDTO> users;
    private ShippingDTO shipping;
    private List<ProductDTO> products;
    private List<PaymentDTO> payment;


}
