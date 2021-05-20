package com.hanshin.shop.mapper;

import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.service.UserService;
import com.hanshin.shop.entity.RoleType;
import com.hanshin.shop.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저 정보 저장")
    void register() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserDto user = UserDto.builder()
                .email("abc@gmail.com")
                .password(bCryptPasswordEncoder.encode("123"))
                .name("hwang")
                .userRoles(Arrays.asList(new UserRole("abc@gmail.com", RoleType.ROLE_MEMBER)))
                .build();

        userService.signUp(user);
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void isExistUser() {
        assertThat(userService.isExist("test1@gmail.com")).isTrue();
    }
}
