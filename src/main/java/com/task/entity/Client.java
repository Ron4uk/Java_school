package com.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBeans object that represents entity of client
 */

@Entity
@Table(name = "clients", uniqueConstraints =@UniqueConstraint(columnNames = {"series", "number"}) )
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Client extends AbstractIdentification {

    @Column (name = "firstname")
    private String firstName;
    @Column (name = "lastname")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "address")
    private String address;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "series")
    private String passport_series;
    @Column(name = "number")
    private  String passport_number;
    @Column(name = "block_client")
    private Boolean blockByClient;
    @Column(name = "block_operator")
    private Boolean blockByOperator;

    @OneToMany (mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Contract> contracts;

    public Client() {
        this.contracts =new HashSet<>();
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
