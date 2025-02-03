package org.example.dao.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class AccountDto {
    Long id;
    Long clientId;
    Double balance;
    String checkAccount;

    String statusAccount;

}
