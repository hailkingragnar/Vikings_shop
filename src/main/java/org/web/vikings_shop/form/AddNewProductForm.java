package org.web.vikings_shop.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AddNewProductForm {
    private String productName;
    private double price;
    private int quantity;
    private String description;
    private String category;
}
