package org.example.dao.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dao.enums.TransactionStatusEnum;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "account_transaction")
public class Transaction {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    Long transactionId;
    Double amountTransaction;
    LocalDateTime timeTransaction;
    TransactionStatusEnum transactionStatusEnum;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "account_id",nullable = false)
    Account account;

}
