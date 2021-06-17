package com.hanshin.shop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    @DisplayName("아이디 중복 확인")
    void isExistUser() {
        assertThat(userService.isExist("test")).isTrue();
    }
}
