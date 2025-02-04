package org.example.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;
import org.example.dao.enums.AccountEnumCheck;
import org.example.dao.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MapperAccount implements Mapper<AccountDto> {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    Account account;

    @Transactional
    @Override
    public boolean mapping(AccountDto accountDto) throws Exception {
        try {
            account.setId(accountDto.getId());
            account.setBalance(accountDto.getBalance());
            try {
                account.setCheckAccount(AccountEnumCheck.fromValue(accountDto.getCheckAccount()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            accountRepository.save(account);


        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}");
            throw new Exception("error kafka");
        }

        return true;
    }
}
