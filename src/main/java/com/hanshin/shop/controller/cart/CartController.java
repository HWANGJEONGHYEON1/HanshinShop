package com.hanshin.shop.controller.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class CartController {

    @GetMapping("/cart")
    public String shop() {
        return "cart/shopping-cart";
    }
}
