package org.example.service.generation;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Account;
import org.example.dao.entity.Client;
import org.example.dao.enums.AccountCheckEnum;
import org.example.dao.enums.AccountStatusEnum;
import org.example.dao.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Service
public class GenerationServiceClientsImpl implements GenerationService{

    @Autowired
    ClientRepository clientRepository;


    @Override
    public boolean generation() throws Exception {
        for (long i = 1; i < 3; i++) {
            Client client = new Client();
            Account account = new Account();

            client.setClientId(i);
            client.setFirstName("Petr " + i);
            client.setLastName("Petrovich");
            client.setMiddleName("Romanov");

            account = getAccount(i, account);
            account.setClient(client);
            client.setAccounts(new ArrayList<>());
            client.getAccounts().add(account);

            log.info("Generate client - {} {}. Number account - {}", client.getFirstName(),
                    client.getLastName(),
                    client.getAccounts().stream().findFirst().get().getAccountId());
            clientRepository.save(client);

        }

        return true;
    }

    private static Account getAccount(long i, Account account) {
        account.setBalance(0.);
        account.setCheckAccount(AccountCheckEnum.DEBIT_ACCOUNT);
        account.setAccountId(i * 5);
        AccountStatusEnum accountStatusEnum;
        switch (new Random().nextInt(2)){
            case 1:
                accountStatusEnum = AccountStatusEnum.BLOCKED;
                break;
            case 2:
                accountStatusEnum = AccountStatusEnum.OPEN;
                break;
            default:
                accountStatusEnum = AccountStatusEnum.OPEN;
                break;
        }
        account.setStatusEnum(accountStatusEnum);
        return account;
    }
}
