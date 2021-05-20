package com.hanshin.shop.mapper;

import com.hanshin.shop.controller.user.UserController;
import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.entity.RoleType;
import com.hanshin.shop.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController controller;

    @Test
    @DisplayName("유저 저장테스트")
    void register() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserDto user = UserDto.builder()
                .email("abcde@gmail.com")
                .password(bCryptPasswordEncoder.encode("123"))
                .name("hwang")
                .userRoles(Arrays.asList(new UserRole("abcde@gmail.com", RoleType.ROLE_MEMBER)))
                .build();
        controller.signUp(user);
    }
}
