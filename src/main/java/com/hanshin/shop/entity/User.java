package com.hanshin.shop.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String account;

    private String password;

    private String tel;

    private String addr;

    private String email;

    private String birth;

    private List<UserRole> userRoles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", roles=" + userRoles.toString() +
                '}';
    }
}
