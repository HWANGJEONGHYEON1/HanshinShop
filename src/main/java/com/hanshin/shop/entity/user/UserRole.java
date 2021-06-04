package com.hanshin.shop.entity;


import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRole {

    private String email;
    private RoleType role;

}
