package org.example.service.mapper;

import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;
import org.mapstruct.*;
import org.springframework.stereotype.Service;


@Service
public interface Mapper<T> {


    public boolean mapping(T object) throws Exception;
}
