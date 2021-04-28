package com.hanshin.shop.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/order")
    public String shopDetail() {
        return "order/checkout";
    }
}
