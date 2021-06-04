package com.hanshin.shop.repository;

import com.hanshin.shop.repository.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper mapper;

    @Test
    @DisplayName("이메일을 통해 유저 정보 잘 가져오는지")
    void userInfoTest() {
        assertThat(mapper.findByEmail("test@gmail.com").getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void isExistEmail() {
        assertThat(mapper.isExistEmail("test@gmail.com")).isEqualTo(1);
        assertThat(mapper.isExistEmail("aaa@gmail.com")).isEqualTo(0);
    }

}
