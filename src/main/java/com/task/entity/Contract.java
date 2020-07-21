package com.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@ToString
public class Contract extends AbstractIdentification {
    @Column(name = "phone")
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    private Authorization auth;
}
