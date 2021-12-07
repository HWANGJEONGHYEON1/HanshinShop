package com.hanshin.shop.repository;

import com.hanshin.shop.IntegrationTests;
import com.hanshin.shop.repository.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTests extends IntegrationTests {

    @Autowired
    private UserMapper mapper;

    @Test
    @DisplayName("이메일을 통해 유저 정보 잘 가져오는지")
    void userInfoTest() {
        assertThat("test").isEqualTo(mapper.findByEmail("test@gmail.com").getName());
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void isExistEmail() {
        assertThat(mapper.isExistEmail("test@gmail.com")).isEqualTo(1);
    }
}
