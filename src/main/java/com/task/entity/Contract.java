package com.task.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@ToString
public class Contract extends AbstractIdentification {
    @Column(name = "phone", unique = true)
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    private Authorization auth;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariff_id", nullable = false)
    private Tariff tariff;
    @Column(name = "block_client")
    private Boolean blockByClient;
    @Column(name = "block_operator")
    private Boolean blockByOperator;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "connected_options",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Option> connectedOptions;

    public Contract() {
        this.connectedOptions = new HashSet<>();
    }
}
