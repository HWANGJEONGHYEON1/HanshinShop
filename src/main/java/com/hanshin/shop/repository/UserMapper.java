package com.hanshin.shop.repository;

import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.entity.User;
import com.hanshin.shop.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findByEmail(String email);

    int isExistEmail(String email);

    int signUp(UserDto user);

    void registerRole(UserRole userRole);
}
