package com.hanshin.shop.service;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.repository.UserMapper;
import com.hanshin.shop.vo.user.RoleType;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.vo.user.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    private String email = "test123@email.com";

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("멤버 생성")
    void member_create() {
        // given
        UserDto mockUserDto = createUser();
        when(userMapper.signUp(any())).thenReturn(1);
        //when
        userService.signUp(mockUserDto);
    }

    private UserDto createUser() {
        UserRole userRole = new UserRole(email, RoleType.ROLE_MEMBER);

        UserDto mockUserDto = UserDto.builder()
                .email(email)
                .password("123")
                .name("hwang")
                .userRoles(Collections.singletonList(userRole))
                .build();
        return mockUserDto;
    }

    @Test
    @DisplayName("이메일로 해당 유저가 있는지 확인")
    void confirm_user_info() {
        UserDto mockUserDto = createUser();
        when(userMapper.signUp(any())).thenReturn(1);
        userService.signUp(mockUserDto);
        when(userMapper.findByEmail(any())).thenReturn(new User().userTransfer(mockUserDto));
        assertThat("hwang").isEqualTo(userService.getUserInfo(email).getName());

        User userInfo = userService.getUserInfo(email);
        Assertions.assertThat(userInfo).isNotNull();
        Assertions.assertThat(userInfo.getName()).isEqualTo("hwang");
    }
}
