package com.hanshin.shop.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class OrderController {

    @GetMapping("/order")
    public String shopDetail() {
        return "order/checkout";
    }
}
