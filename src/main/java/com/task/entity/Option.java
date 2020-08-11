package com.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "options", uniqueConstraints =@UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Option extends AbstractIdentification{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price", precision = 6, scale = 2, nullable = false)
    private BigDecimal price;
    @Column(name = "connection_cost", precision = 6, scale = 2, nullable = false)
    private BigDecimal connectionCost;
    @Column(name = "deleted", nullable = false)
    private Boolean deleted=false;
    @JoinTable(name = "required_options")
    @ManyToMany(cascade = CascadeType.REFRESH,  fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> requiredOptions;
    @JoinTable (name = "exclusion_options")
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> exclusionOptions;
    @ManyToMany(mappedBy = "options",  fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Tariff> tariffs;
    @ManyToMany(mappedBy = "connectedOptions",  fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Contract> contracts;


}
