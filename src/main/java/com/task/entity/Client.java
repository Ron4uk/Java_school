package com.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

/**
 * Simple JavaBeans object that represents entity of client
 */

@Entity
@Table(name = "clients", uniqueConstraints =@UniqueConstraint(columnNames = {"series", "number"}) )
@Getter
@Setter
@ToString
public class Client extends AbstractIdentification {

    @Column (name = "firstname")
    private String firstName;
    @Column (name = "lastname")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "email")
    private String email;
    @Column(name = "series")
    private String passport_series;
    @Column(name = "number")
    private  String passport_number;

    public Client() {
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
