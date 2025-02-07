package org.example.service.mapper;

import org.example.dao.dto.ClientDto;
import org.example.dao.entity.Account;
import org.example.dao.entity.Transaction;

import java.time.LocalDateTime;

public class MapperClient {
    public ClientDto mapping(Account account, Transaction transaction) throws Exception {
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(account.getClient().getClientId());
        clientDto.setAccountId(account.getId());
        clientDto.setTransactionId(transaction.getTransactionId());
        clientDto.setTimestamp(LocalDateTime.now());
        clientDto.setAccountBalance(getAccountBalanse(account, transaction));
        return clientDto;
    }

    private Double getAccountBalanse(Account account, Transaction transaction) {
        double amountTransaction = transaction.getAmountTransaction();
        double balance = account.getBalance();
        balance += amountTransaction;
        return balance;
    }
}
