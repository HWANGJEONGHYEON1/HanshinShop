package com.hanshin.shop.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String shop() {
        return "cart/shopping-cart";
    }
}
