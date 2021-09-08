package com.hanshin.shop.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private Long id;

    private String name;

    private String account;

    @JsonIgnore
    private String password;

    private String tel;

    private String address;

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
                ", addr='" + address + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", roles=" + userRoles.toString() +
                '}';
    }
}
