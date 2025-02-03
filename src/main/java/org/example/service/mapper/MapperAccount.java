package org.example.service.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;
import org.example.dao.enums.AccountCheckEnum;
import org.example.dao.enums.AccountStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MapperAccount {



    @Transactional
    public Account mapping(AccountDto accountDto) throws Exception {
        Account account = new Account();
        try {
            account.setAccountId(accountDto.getId());
            account.setBalance(accountDto.getBalance());
//            account.setClientId(accountDto.getClientId());
            try {
                account.setCheckAccount(AccountCheckEnum.fromValue(accountDto.getCheckAccount()));
                account.setStatusEnum(AccountStatusEnum.fromValue(accountDto.getStatusAccount()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception exception) {
            log.error("Ошибка Topic: {}, Key: {}");
            throw new Exception("error kafka");
        }

        return account;
    }

}
