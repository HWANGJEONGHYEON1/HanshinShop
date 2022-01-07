package com.hanshin.shop.controller;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.config.jwt.JwtFilter;
import com.hanshin.shop.config.jwt.TokenProvider;
import com.hanshin.shop.controller.user.dto.LoginDto;
import com.hanshin.shop.service.UserService;
import com.hanshin.shop.vo.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Test
    @DisplayName("authentication api > 디비에 있는 회원인지 확인")
    void authentication() throws Exception {
        LoginDto loginUser = LoginDto.builder()
                .username("test@gmail.com")
                .password("test") // bcrypt 암호화를 통해 비밀번호를 비교하기JwtFilter 때문에 실제 디비에는 암호화 된 비밀번호가 저장되어있다.
                .build();

        mvc.perform(post("/api/authenticate")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(loginUser))
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("authority api > 로그인 시 패스워드가 틀렸을 경우 에러")
    void authentication_wrong_password() throws Exception {
        LoginDto loginUser = LoginDto.builder()
                .username("test@gmail.com")
                .password("옳지앟은비밀번호") // bcrypt 암호화를 통해 비밀번호를 비교하기 때문에 실제 디비에는 암호화 된 비밀번호가 저장되어있다.
                .build();

        mvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginUser))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("jwt token이 유효한 데이터가 아닐경우 401")
    void authentication_jwt_invalid() throws Exception {
        User userInfo = userService.getUserInfo("test@gmail.com");

        Cookie cookie = new Cookie("Authorization", "invalidToken");

        mvc.perform(get("/cart/count/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(userInfo))
                .cookie(cookie)
        ).andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("jwt token이 유효하나 접근권한이 아닐경우 403")
    void authentication_jwt_access_denied() throws Exception {
        User userInfo = userService.getUserInfo("test@gmail.com");

        System.out.println(userInfo);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userInfo.getEmail(), "test");

        // authenticationToken을 이용해서 authenticate 메소드가 실행될 때 loadUserByUsername이 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        final Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, jwt);


        mvc.perform(get("/cart/count/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(userInfo))
                        .cookie(cookie)
                ).andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("jwt token이 유효하나 접근권한이 맞을경우 성공")
    void authentication_jwt_access_success() throws Exception {
        User userInfo = userService.getUserInfo("test1@naver.com");

        System.out.println(userInfo);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userInfo.getEmail(), "test1");

        // authenticationToken을 이용해서 authenticate 메소드가 실행될 때 loadUserByUsername이 실행된다.
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        final Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, jwt);


        mvc.perform(get("/api/cart/count/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(userInfo))
                        .cookie(cookie)
                ).andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", ArgumentMatchers.eq(1)))
                .andDo(print());
    }
}
