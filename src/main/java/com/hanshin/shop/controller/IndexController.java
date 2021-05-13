package com.hanshin.shop.controller;

import com.hanshin.shop.utill.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        final String username = SecurityUtil.getCurrentUsername().get();
        if (!username.equals("anonymousUser")) {
            model.addAttribute("userName", username);
        }

        return "index";
    }
}
