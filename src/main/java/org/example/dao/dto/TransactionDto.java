package org.example.dao.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.example.dao.enums.TransactionStatusEnum;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class TransactionDto {
    long accountId;
    long transactionId;
    String transactionStatusEnum;
}
