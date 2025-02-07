package org.example.dao.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum AccountStatusEnum {

    ARRESTED("ARRESTED"),
    BLOCKED("BLOCKED"),
    CLOSED("CLOSED"),
    OPEN("OPEN");


    private final String value;

    @JsonCreator
    public static AccountStatusEnum fromValue(String value) throws Exception {

        for (AccountStatusEnum status : AccountStatusEnum.values()) {
            if (status.value.equals(value)) {
                log.info("Status is - {}",status);
                return status;
            }
        }
        throw new Exception("Unknown value: " + value);
    }
}
