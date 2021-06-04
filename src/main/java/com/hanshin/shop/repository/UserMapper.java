package com.hanshin.shop.repository;

import com.hanshin.shop.controller.user.dto.UserDto;
import com.hanshin.shop.entity.user.User;
import com.hanshin.shop.entity.user.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByEmail(String email);

    int isExistEmail(String email);

    int signUp(UserDto user);

    void registerRole(UserRole userRole);
}
