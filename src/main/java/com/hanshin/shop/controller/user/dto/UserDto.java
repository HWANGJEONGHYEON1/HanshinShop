package com.hanshin.shop.controller.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanshin.shop.entity.UserRole;
import com.sun.istack.NotNull;
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

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    private String account;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 3, max = 50)
    private String password;

    private String tel;

    private String addr;

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    private String birth;

    private List<UserRole> userRoles;


}
