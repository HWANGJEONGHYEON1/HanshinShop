package com.hanshin.shop.controller.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class BlogDetailController {

    @GetMapping("/blogDetail")
    public String blog() {
        return "blog/blog-details";
    }
}
