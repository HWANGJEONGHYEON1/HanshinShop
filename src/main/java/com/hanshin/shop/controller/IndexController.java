package com.hanshin.shop.controller;

import com.hanshin.shop.entity.LoginUser;
import com.hanshin.shop.entity.User;
import com.hanshin.shop.utill.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, @LoginUser User user) {
        final String username = SecurityUtil.getCurrentUsername().get();

        if (!username.equals("anonymousUser")) {
            log.info(user.getName());
            log.info(user.getEmail());
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/a")
    public String a() {
        throw new IllegalStateException("부정확한 URL입니다.");
    }
}
