package org.web.vikings_shop.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  UserForm {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gender;
    private String occupation;
}
