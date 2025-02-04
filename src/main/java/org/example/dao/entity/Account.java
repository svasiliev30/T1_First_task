package org.example.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.example.dao.enums.AccountEnumCheck;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@Entity
@Table(name = "сlients_account")
public class Account {
    @Id
    @Column(nullable = false)
//    @Column(name = "account_id", nullable = false)
    Long id;
    Integer balance;
    AccountEnumCheck checkAccount;



}
