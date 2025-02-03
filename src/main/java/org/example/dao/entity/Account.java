package org.example.dao.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dao.enums.AccountCheckEnum;
import org.example.dao.enums.AccountStatusEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@Entity
@Table(name = "сlients_account")
public class Account {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(name = "account_id",nullable = false, unique = true)
    private Long accountId;
    private Double balance;
    private AccountCheckEnum checkAccount;

    private AccountStatusEnum statusEnum;

    private List<Double> frozenAmount = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<Transaction> transaction = new HashSet<>();


}
