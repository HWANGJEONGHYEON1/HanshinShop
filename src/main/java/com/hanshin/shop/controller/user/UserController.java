package com.hanshin.shop.controller.user;

import com.hanshin.shop.config.jwt.JwtFilter;
import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.entity.User;
import com.hanshin.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<User> signUp(@Validated @RequestBody UserDto userDto) {
        log.info("회원가입 " + userDto);

        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
    public ResponseEntity<User> getMyUserInfo(@PathVariable String username) {

        return ResponseEntity.ok(userService.getUserInfo(username));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> getAnotherUserInfo() {
        return ResponseEntity.ok("useradmin");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        eraseCookie(request, response);

        return ResponseEntity.ok("logout");
    }

    private void eraseCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


    // 추후 멤버와 어드민 권한만 가지고 있을 때 메서드 호출 주문하기 등.
    // @PreAuthorize("hasAnyRole('MEMBER','ADMIN')") ~
}