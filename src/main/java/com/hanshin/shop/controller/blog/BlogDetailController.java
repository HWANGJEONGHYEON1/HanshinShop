package com.hanshin.shop.controller.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogDetailController {

    @GetMapping("/blogDetail")
    public String blog() {
        return "blog/blog-details";
    }
}
