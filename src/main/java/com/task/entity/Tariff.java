package com.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tariffs")
@EqualsAndHashCode
public class Tariff extends AbstractIdentification {
    @Column(name = "tariff", nullable = false)
    private String tariff;
    @Column(name = "price", precision = 6, scale = 2, nullable = false)
    private BigDecimal price;
    @OneToMany (mappedBy = "tariff")
    @ToString.Exclude
    private Set<Contract> contracts;
    @ManyToMany( cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "options_in_tariff",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    @Fetch(value = FetchMode.SELECT)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> options;



}
