package com.hanshin.shop.service;

import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.vo.user.User;
import com.hanshin.shop.repository.UserMapper;
import com.hanshin.shop.vo.user.RoleType;
import com.hanshin.shop.vo.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper mapper;
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public boolean isExist(String email) {
        return mapper.isExistEmail(email) >= 1;
    }

    @Transactional
    public User signUp(UserDto user) {
        if (isExist(user.getEmail())) {
            throw new RuntimeException("이미 가입되어있는 유저입니다.");
        }
        saveUserRole(user);
        mapper.signUp(saveUser(user));

        return getUserInfo(user.getEmail());
    }

    private UserDto saveUser(UserDto user) {
        return UserDto.builder()
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .address(user.getAddress())
                .account(user.getAccount())
                .birth(user.getBirth())
                .tel(user.getTel())
                .userRoles(user.getUserRoles())
                .build();
    }

    private void saveUserRole(UserDto user) {
        UserRole role = new UserRole();
            role.setRole(RoleType.ROLE_MEMBER);
            role.setEmail(user.getEmail());
        mapper.registerRole(role);
    }

    @Cacheable(key = "#username", value = "getUser")
    public User getUserInfo(String username) {
        return mapper.findByEmail(username);
    }
}
