package org.example.service;

import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;

import java.util.List;

public interface AccountService  {

    Account getAccount (Long id) throws Exception;
    Account createAccount(AccountDto account) throws Exception;
    Long deleteAccount (Long id) throws Exception;
    Account updateAccount(AccountDto account) throws Exception;
}
