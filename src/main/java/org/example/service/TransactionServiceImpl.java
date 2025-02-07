package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Transaction;
import org.example.dao.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service("TransactionServiceImpl")
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private Transaction transaction;

    @Transactional
    @Override
    public Transaction getTransaction(Long id) throws Exception {
        log.info("We want to find the transaction with the id - " + id);
        if (transactionRepository.findById(id).isEmpty()) {
            throw new Exception("Erorr");
        }
        transaction = transactionRepository.findById(id).get();
        log.info("We found a transaction - " + transaction.toString());
        return transaction;
    }

    @Transactional
    @Override
    public Transaction createTransaction(Transaction newTransaction) throws Exception {
        log.info("Сreating a transaction - " + newTransaction.getId());
//        if (!transactionRepository.findById(newTransaction.getId()).isEmpty()) {
//            throw new Exception("Erorr");
//        }
//        transaction.setId(newTransaction.getId());
//        transaction.setAmountTransaction(newTransaction.getAmountTransaction());
//        transaction.setTimeTransaction(newTransaction.getTimeTransaction());
//        transactionRepository.save(transaction);
//        log.info("We can save transaction - " + transaction.getId());
        return transaction;
    }

    @Transactional
    @Override
    public Long deleteTransaction(Long id) throws Exception {
        log.info("We want to find the transaction with the id - " + id);
        if (transactionRepository.findById(id).isEmpty()) {
            throw new Exception("Erorr");
        }
        transactionRepository.deleteById(id);
        log.info("We delete a transaction - " + transaction.toString());
        return id;
    }


}
