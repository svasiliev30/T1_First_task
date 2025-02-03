package org.example.dao.repository;

import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Transaction findByTransactionId(Long transactionId);
}
