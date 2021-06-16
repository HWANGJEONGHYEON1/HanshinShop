package com.hanshin.shop.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
