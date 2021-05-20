package com.hanshin.shop.service;

import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.entity.User;
import com.hanshin.shop.repository.UserMapper;
import com.hanshin.shop.entity.RoleType;
import com.hanshin.shop.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper mapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public boolean isExist(String email) {
        return mapper.isExistEmail(email) >= 1;
    }

    public User signUp(UserDto user) {
        if (isExist(user.getEmail())) {
            throw new RuntimeException("이미 가입되어있는 유저입니다.");
        }

        saveUserRole(user);
        int signUpCount = mapper.signUp(
                UserDto.builder()
                        .email(user.getEmail())
                        .password(bCryptPasswordEncoder.encode(user.getPassword()))
                        .name(user.getName())
                        .addr(user.getAddr())
                        .account(user.getAccount())
                        .birth(user.getBirth())
                        .tel(user.getTel())
                        .userRoles(user.getUserRoles())
                        .build()
        );

        if (signUpCount == 1) {
            return getUserInfo(user.getEmail());
        }

        throw new RuntimeException("관리자에게 문의하세요.");
    }

    private void saveUserRole(UserDto user) {
        UserRole role = new UserRole();
            role.setRole(RoleType.ROLE_MEMBER);
            role.setEmail(user.getEmail());
        mapper.registerRole(role);
    }

    public User getUserInfo(String username) {
        return mapper.findByEmail(username).get();
    }
}
