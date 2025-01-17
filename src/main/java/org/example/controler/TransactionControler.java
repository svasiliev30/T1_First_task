package org.example.controler;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Transaction;
import org.example.dao.repository.TransactionRepository;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/transaction")
public class TransactionControler {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    Transaction transaction;

    @GetMapping("/findTransactionById")
    public ResponseEntity<Transaction> findTransaction(@RequestParam Long id) throws Exception {
        transaction = transactionService.getTransaction(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws Exception {
        this.transaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTransactions")
    public ResponseEntity<Long> deleteTransaction(@RequestParam Long id) throws Exception {

        Long idAccount = transactionService.deleteTransaction(id);
        return new ResponseEntity<>(idAccount, HttpStatus.OK);
    }

}
