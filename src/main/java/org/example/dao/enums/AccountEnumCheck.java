package org.example.dao.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountEnumCheck {
    DEBIT_ACCOUNT("DEBIT_ACCOUNT"),
    CREDIT_ACCOUNT("CREDIT_ACCOUNT");
    private final String value;

    @JsonCreator
    public static AccountEnumCheck fromValue(String value) throws Exception {
        for (AccountEnumCheck status : AccountEnumCheck.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new Exception("Unknown value: " + value);
    }
}
