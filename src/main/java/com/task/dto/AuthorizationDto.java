package com.task.dto;

import com.task.entity.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthorizationDto implements DtoEntity {
    private Integer id;
    @ToString.Exclude
    private String password;
    private Set<Role> roles;

    public AuthorizationDto() {
        this.roles = new HashSet<>();
    }
}
