package com.hanshin.shop.controller;

import com.hanshin.shop.entity.user.LoginUser;
import com.hanshin.shop.entity.user.User;
import com.hanshin.shop.utill.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    @GetMapping(value = {"/", "/index"})
    public String index(Model model, @LoginUser User user) {
        return "index";
    }

    @GetMapping("/api/user/info")
    @ResponseBody
    public ResponseEntity<User> info(@LoginUser User user) {
        log.info("##### {}", user);
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(new User(), HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/a")
    public String a() {
        throw new IllegalStateException("부정확한 URL입니다.");
    }
}
