package org.example.controler;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(value = "/transaction")
public class TransactionControler {

    private final TransactionRepository transactionRepository;

    private final TransactionService transactionService;


    @GetMapping("/findTransactionById")
    public ResponseEntity<Transaction> findTransaction(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(transactionService.getTransaction(id), HttpStatus.OK);
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws Exception {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTransactions")
    public ResponseEntity<Long> deleteTransaction(@RequestParam Long id) throws Exception {

        return new ResponseEntity<>(transactionService.deleteTransaction(id), HttpStatus.OK);
    }

}
