package org.example.dao.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ClientDto {

    Long clientId;
    Long accountId;

    Long transactionId;

    LocalDateTime timestamp;

    Double transactionAmount;

    Double accountBalance;
}
