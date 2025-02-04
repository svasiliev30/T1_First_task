package org.example.service;

import org.example.dao.entity.Transaction;

public interface TransactionService {

    Transaction getTransaction(Long id) throws Exception;

    Transaction createTransaction(Transaction transaction) throws Exception;

    Long deleteTransaction(Long id) throws Exception;
}
