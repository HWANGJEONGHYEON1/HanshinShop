package com.hanshin.shop.controller.user;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "/login/customLogin";
    }

    @GetMapping("/signup")
    public String register() {
        return "/login/signup";
    }
}
