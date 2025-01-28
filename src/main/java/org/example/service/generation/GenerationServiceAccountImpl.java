package org.example.service.generation;

import org.example.dao.dto.AccountDto;
import org.example.kafka.KafkaProducerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerationServiceAccountImpl implements GenerationService {

    @Autowired
    KafkaProducerAccount kafkaProducerAccount;

    @Override
    public boolean generation() throws Exception {

        for (long i = 10; i < 15; i++) {
            AccountDto accountDto = new AccountDto();
            accountDto.setId(i);
            accountDto.setBalance(new Random().nextInt(10000));
            if (i % 2 == 0) {
                accountDto.setCheckAccount("DEBIT_ACCOUNT");
            } else {
                accountDto.setCheckAccount("CREDIT_ACCOUNT");
            }
            kafkaProducerAccount.send(accountDto);
        }

        return true;
    }
}
