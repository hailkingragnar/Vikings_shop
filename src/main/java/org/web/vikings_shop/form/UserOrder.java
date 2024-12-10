package org.web.vikings_shop.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {
    private String recipientName;
    private String recipientEmail;
    private String phone;
    private String country;
    private String state;
    private String city;
    private String addressLineOne;
    private String zipCode;

}
