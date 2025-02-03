package org.example.dao.enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum TransactionStatusEnum {

    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    BLOCKED("BLOCKED"),
    CANCELLED("CANCELLED"),
    REQUESTED("REQUESTED");


    private final String value;

    @JsonCreator
    public static TransactionStatusEnum fromValue(String value) throws Exception {

        for (TransactionStatusEnum status : TransactionStatusEnum.values()) {
            if (status.value.equals(value)) {
                log.info("Status is - {}",status);
                return status;
            }
        }
        throw new Exception("Unknown value: " + value);
    }
}