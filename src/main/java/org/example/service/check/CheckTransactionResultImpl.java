package org.example.service.check;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.TransactionDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;
import org.example.dao.enums.TransactionStatusEnum;
import org.example.dao.repository.AccountRepository;
import org.example.dao.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("CheckTransactionResultImpl")
public class CheckTransactionResultImpl implements CheckStatus<TransactionDto> {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void getCheck(TransactionDto transactionDto) throws Exception {
        try {
            switch (TransactionStatusEnum.fromValue(transactionDto.getTransactionStatusEnum())) {
                case ACCEPTED:
                    statusAccepted(transactionDto);
                    break;
                case REJECTED:
                    statusRejected(transactionDto);
                    break;
                case BLOCKED:
                    statusBlocked(transactionDto);
                    break;
            }
        } catch (Exception e) {
            log.error("Error check transaction client with transaction id - {} and number account - {}",
                    transactionDto.getTransactionId(), transactionDto.getAccountId());
            throw new Exception();
        }
    }

    public void statusAccepted(TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionDto.getTransactionId());
        Optional<Account> accountOpt = accountRepository.findByAccountId(transaction.getAccount().getAccountId());
        Account account = accountOpt.get();

        double balance = account.getBalance() + transaction.getAmountTransaction();
        log.info("Transaction is accepted");
        account.setBalance(balance);
        accountRepository.save(account);

        transaction.setTransactionStatusEnum(TransactionStatusEnum.ACCEPTED);
        transactionRepository.save(transaction);
    }

    public void statusRejected(TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionDto.getTransactionId());
        Optional<Account> accountOpt = accountRepository.findByAccountId(transaction.getAccount().getAccountId());
        Account account = accountOpt.get();

        log.info("Transaction is rejected");
        double balance = account.getBalance() + transaction.getAmountTransaction();
        account.setBalance(balance);
        accountRepository.save(account);

        transaction.setTransactionStatusEnum(TransactionStatusEnum.ACCEPTED);
        transactionRepository.save(transaction);
    }

    public void statusBlocked(TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionDto.getTransactionId());
        Optional<Account> accountOpt = accountRepository.findByAccountId(transaction.getAccount().getAccountId());
        Account account = accountOpt.get();

        log.info("Transaction is blocked");
        double balance = account.getBalance() + transaction.getAmountTransaction();
        account.getFrozenAmount().add(balance);
        accountRepository.save(account);

        transaction.setTransactionStatusEnum(TransactionStatusEnum.BLOCKED);
        transactionRepository.save(transaction);
    }
}
