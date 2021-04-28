package com.hanshin.shop.controller.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String shopDetail() {
        return "contact/contact";
    }
}
