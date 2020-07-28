package com.task.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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
    @Column(name = "deprecated")
    private Boolean deprecated;
    @ManyToMany( cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "options_in_tariff",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    @Fetch(value = FetchMode.SELECT)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> options;



}
