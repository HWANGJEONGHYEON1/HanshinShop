package com.hanshin.shop.service;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.vo.user.RoleType;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.vo.user.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest extends IntegrationTests {

    @Autowired
    private UserService userService;

    private String email = "test@email.com";

    @BeforeEach
    public void createUser() {
        UserRole userRole = new UserRole(email, RoleType.ROLE_MEMBER);

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword("123");
        userDto.setName("hwang");
        userDto.setUserRoles(Collections.singletonList(userRole));
        userService.signUp(userDto);
    }

    @Test
    @DisplayName("유저 생성 확인")
    @Transactional(readOnly = true)
    void exist_user() {
        assertThat(userService.isExist(email)).isTrue();
    }

    @Test
    @DisplayName("이메일로 해당 유저가 있는지 확인")
    @Transactional(readOnly = true)
    void confirm_user_info() {
        assertThat(userService.getUserInfo(email).getName()).isEqualTo("hwang");
    }
}
