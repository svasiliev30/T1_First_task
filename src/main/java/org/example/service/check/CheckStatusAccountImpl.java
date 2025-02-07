package org.example.service.check;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.entity.Account;
import org.example.dao.enums.AccountStatusEnum;

@Slf4j
public class CheckStatusAccountImpl implements Check<Account> {
    @Override
    public boolean getCheck(Account account) {
        Long id = account.getId();
        log.info("Check account with id - {}", id);
        try {
            if (account.getStatusEnum().equals(AccountStatusEnum.OPEN)) {
                log.info("status account is open");
                return true;
            } else {
                log.info("status account is not open");
                return false;
            }
        } catch (Exception exception) {
            log.error("error checking status account with this ID - {}", id);
            throw new NullPointerException();
        }
    }
}
