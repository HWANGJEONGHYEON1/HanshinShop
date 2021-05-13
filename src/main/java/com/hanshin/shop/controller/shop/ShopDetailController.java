package com.hanshin.shop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopDetailController {

    @GetMapping("/shop-details")
    public String shopDetail() {
        return "shop/shop-details";
    }

}
