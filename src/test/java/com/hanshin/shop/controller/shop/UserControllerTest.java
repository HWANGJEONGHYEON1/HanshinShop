package com.hanshin.shop.controller.shop;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.controller.user.dto.LoginDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTests {

    private MockHttpServletResponse mockHttpServletResponse;

    @Test
    @DisplayName("authentication api > 디비에 있는 회원인지 확인")
    void authentication() throws Exception {
        LoginDto loginUser = LoginDto.builder()
                .username("test@gmail.com")
                .password("test") // bcrypt 암호화를 통해 비밀번호를 비교하기 때문에 실제 디비에는 암호화 된 비밀번호가 저장되어있다.
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
}
