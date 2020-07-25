package com.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "options")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Option extends AbstractIdentification{
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "connection_cost")
    private Integer connectionCost;
    @JoinTable(name = "required_options")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Option> requiredOptions;
    @JoinTable (name = "exclusion_options")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Option> exclusionOptions;
}
