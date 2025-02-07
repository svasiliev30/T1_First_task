package org.example.service.generation;

import lombok.RequiredArgsConstructor;
import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;
import org.example.dao.enums.TransactionStatusEnum;
import org.example.kafka.testProduser.KafkaProducerTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerationServiceTransactionImpl implements GenerationService {

    private final KafkaProducerTransaction kafkaProducerTransaction;

    @Override
    public boolean generation() throws Exception {
        Account account = new Account();
        for (long i = 10; i < 15; i++) {
            Transaction transaction = new Transaction();
            transaction.setId(i);
            transaction.setAmountTransaction(new Random().nextDouble(10000));
            transaction.setTimeTransaction(LocalDateTime.now());
            transaction.setTransactionId(new Random().nextLong(99999999999999999L));

            TransactionStatusEnum[] statusEnum = TransactionStatusEnum.values();
            TransactionStatusEnum randomStatus = statusEnum[new Random().nextInt(TransactionStatusEnum.values().length)];
            transaction.setTransactionStatusEnum(randomStatus);

            account.setAccountId(i+10);
            transaction.setAccount(account);

            kafkaProducerTransaction.send(transaction);
        }

        return true;
    }
}
