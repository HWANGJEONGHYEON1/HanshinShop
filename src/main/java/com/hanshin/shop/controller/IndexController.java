package com.hanshin.shop.controller;

import com.hanshin.shop.vo.user.LoginUser;
import com.hanshin.shop.vo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, @LoginUser User user) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login/customLogin";
    }

    @GetMapping("/signup")
    public String register() {
        return "/login/signup";
    }

    @GetMapping("/member/cart")
    public String cart(@RequestParam(value = "userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "cart/shopping-cart";
    }

    @GetMapping("/goods/new")
    public String registerForm() {
        return "goods/createProduct";
    }

    @GetMapping("/member/order")
    public String shopDetail() {
        return "order/order-goods";
    }
}
