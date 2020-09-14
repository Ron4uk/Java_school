package com.task.entity;

import lombok.*;

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
@RequiredArgsConstructor
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


    @OneToMany (mappedBy = "client")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Contract> contracts=new HashSet<>();;






}
