package com.hanshin.shop.controller.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ContactController {

    @GetMapping("/contact")
    public String shopDetail() {
        return "contact/contact";
    }
}
