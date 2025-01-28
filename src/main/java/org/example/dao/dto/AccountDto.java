package org.example.dao.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dao.enums.AccountEnumCheck;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class AccountDto {
    Long id;
    Integer balance;
    String checkAccount;

}
