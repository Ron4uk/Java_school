package com.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBeans object that represents entity of Authorization
 */
@Entity
@Table(name = "authorizations")
@Getter
@Setter
@ToString
public class Authorization extends AbstractIdentification {

    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;



    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "auth_roles", joinColumns = @JoinColumn(name="auth_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;




}
