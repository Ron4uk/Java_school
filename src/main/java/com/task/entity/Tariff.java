package com.task.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tariffs")
public class Tariff extends AbstractIdentification {
    @Column(name = "tariff")
    private String tariff;
    @Column(name = "price", precision = 4, scale = 2)
    private double price;
    @Column(name = "deprecated")
    private boolean deprecated;
}
