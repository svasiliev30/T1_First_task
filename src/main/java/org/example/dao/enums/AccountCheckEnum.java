package org.example.dao.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum AccountCheckEnum {
    DEBIT_ACCOUNT("DEBIT_ACCOUNT"),
    CREDIT_ACCOUNT("CREDIT_ACCOUNT");
    private final String value;

    @JsonCreator
    public static AccountCheckEnum fromValue(String value) throws Exception {
        for (AccountCheckEnum status : AccountCheckEnum.values()) {
            if (status.value.equals(value)) {
                log.info("Status is - {}",status);
                return status;
            }
        }
        throw new Exception("Unknown value: " + value);
    }
}
