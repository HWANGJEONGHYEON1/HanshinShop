package com.hanshin.shop.service;

import com.hanshin.shop.dto.UserDto;
import com.hanshin.shop.entity.User;
import com.hanshin.shop.repository.UserMapper;
import com.hanshin.shop.entity.RoleType;
import com.hanshin.shop.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper mapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public boolean isExist(String email) {
        if (mapper.isExistEmail(email) >= 1) {
            return true;
        }
        return false;
    }

    public Optional<User> signUp(UserDto user) {
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

        return Optional.empty();
    }

    private void saveUserRole(UserDto user) {
        UserRole role = new UserRole();
            role.setRole(RoleType.ROLE_MEMBER);
            role.setEmail(user.getEmail());
        mapper.registerRole(role);
    }

    public Optional<User> getUserInfo(String username) {
        return mapper.findByEmail(username);
    }
}
