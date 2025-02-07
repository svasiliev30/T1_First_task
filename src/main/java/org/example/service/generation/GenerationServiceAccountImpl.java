package org.example.service.generation;

import lombok.RequiredArgsConstructor;
import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Client;
import org.example.dao.enums.AccountCheckEnum;
import org.example.dao.enums.AccountStatusEnum;
import org.example.kafka.testProduser.KafkaProducerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class GenerationServiceAccountImpl implements GenerationService {

    public final KafkaProducerAccount kafkaProducerAccount;

    @Override
    public boolean generation() throws Exception {

        for (long i = 10; i < 15; i++) {
            Account account = new Account();
            Client client = new Client();
            client.setClientId(1L);
            account.setId(i);
            account.setAccountId(i+10);
            account.setBalance(0.);
            account.setClient(client);
            if (i % 2 == 0) {
                account.setCheckAccount(AccountCheckEnum.DEBIT_ACCOUNT);
                account.setStatusEnum(AccountStatusEnum.OPEN);
            } else {
                account.setCheckAccount(AccountCheckEnum.CREDIT_ACCOUNT);
                account.setStatusEnum(AccountStatusEnum.BLOCKED);
            }

            kafkaProducerAccount.send(account);
        }

        return true;
    }
}
