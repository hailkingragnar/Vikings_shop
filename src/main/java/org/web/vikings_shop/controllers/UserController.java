package org.web.vikings_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userhome")
    public String userHome(){
        return "user/userhome";
    }

}
