package com.hanshin.shop.controller.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanshin.shop.vo.user.UserRole;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @Size(min = 1, max = 50)
    private String name;

    private String account;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 50)
    private String password;

    private String tel;

    private String address;

    @Size(min = 3, max = 50)
    private String email;

    private String birth;

    private List<UserRole> userRoles;


}
