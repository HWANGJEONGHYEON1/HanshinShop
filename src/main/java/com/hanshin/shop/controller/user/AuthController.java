package com.hanshin.shop.controller.user;

import com.hanshin.shop.config.jwt.JwtFilter;
import com.hanshin.shop.config.jwt.TokenProvider;
import com.hanshin.shop.controller.user.dto.LoginDto;
import com.hanshin.shop.controller.user.dto.TokenDto;
import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.vo.user.LoginUser;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // authenticationToken을 이용해서 authenticate 메소드가 실행될 때 loadUserByUsername이 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        final Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, jwt);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ResponseEntity<>(new TokenDto(jwt), HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public User signUp(@Validated @RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        eraseCookie(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void eraseCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @GetMapping("/user/info")
    public ResponseEntity<User> info(@LoginUser User user) {
        if (Objects.isNull(user)) {
            return new ResponseEntity<>(new User(), HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER','ROLE_ADMIN')")
    public ResponseEntity<User> getMyUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserInfo(username));
    }
}