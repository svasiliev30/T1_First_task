package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;
import org.example.dao.enums.AccountCheckEnum;
import org.example.dao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("AccountServiceImpl")
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    Account account;

    @Transactional
    @Override
    public Account getAccount(Long id) throws Exception {
        log.info("We want to find the user with the id - " + id);
        if (accountRepository.findById(id).isEmpty()) {
            throw new Exception("Erorr");
        }
        account = accountRepository.findById(id).get();
        log.info("We found a client - " + account.toString());
        return account;
    }

    //    @SneakyThrows
    @Transactional
    @Override
    public Account createAccount(AccountDto accountDto) throws Exception {
        log.info("We want to creating a client account - " + accountDto.getId());
        if (!accountRepository.findById(accountDto.getId()).isEmpty()) {
            throw new Exception("Erorr");
        }
//        account.setId(accountDto.getId());
//        account.setBalance(accountDto.getBalance());
//        account.setCheckAccount(AccountCheckEnum.fromValue(accountDto.getCheckAccount()));
        accountRepository.save(account);
        log.info("We can save account - " + accountDto.getId());
        return account;
    }

    @Transactional
    @Override
    public Long deleteAccount(Long id) throws Exception {
        log.info("We want to find the user with the id - " + id);
//        if (accountRepository.findById(id).isEmpty()) {
//            throw new Exception("Erorr");
//        }
//        accountRepository.deleteById(id);
//        log.info("We delete a client - " + id);
        return id;
    }

    @Transactional
    @Override
    public Account updateAccount(AccountDto accountDto) throws Exception {
        log.info("We want to update a client account - " + accountDto.getId());
        if (accountRepository.findById(accountDto.getId()).isEmpty()) {
            throw new Exception("Erorr");
        }
//        account.setId(accountDto.getId());
//        account.setBalance(accountDto.getBalance());
//        account.setCheckAccount(AccountCheckEnum.fromValue(accountDto.getCheckAccount()));
//        accountRepository.save(account);
        log.info("We can save account - " + accountDto.getId());
        return account;
    }
}
