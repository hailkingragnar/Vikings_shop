package org.web.vikings_shop.controllers;

import org.web.vikings_shop.entities.User;
import org.web.vikings_shop.form.UserForm;
import org.web.vikings_shop.service.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class PageController {
    @Autowired
    private UserRegistration userRegistration;

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
    return "signup";
}
     @RequestMapping("/do-register")
    public String doRegister(@ModelAttribute UserForm userForm){

         User user = new User();
         user.setName( userForm.getName());
         user.setEmail( userForm.getEmail());
         user.setAddress( userForm.getAddress());
         user.setPhone(userForm.getPhone());
         user.setGender(userForm.getGender());
         user.setPassword(userForm.getPassword());
         userRegistration.saveUser(user);

          return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


}
