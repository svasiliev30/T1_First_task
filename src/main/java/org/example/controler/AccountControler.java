package org.example.controler;

import lombok.RequiredArgsConstructor;
import org.example.dao.dto.AccountDto;
import org.example.dao.entity.Account;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor
public class AccountControler {



    private final AccountService accountService;


    @GetMapping("/findClientById")
    public ResponseEntity<Account> findClient(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(accountService.getAccount(id),HttpStatus.OK);
    }


    @PostMapping("/createClient")
    public ResponseEntity<Account> createClient(@RequestBody AccountDto accountDto) throws Exception {
        return new ResponseEntity<>(accountService.createAccount(accountDto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient")
    public ResponseEntity<Long> deleteClient(@RequestParam Long id) throws Exception {
        return new ResponseEntity<>(accountService.deleteAccount(id),HttpStatus.OK);
    }

    @PutMapping("/updateClient")
    public ResponseEntity<Account> udateClient(@RequestBody AccountDto accountDto) throws Exception {
        return new ResponseEntity<>(accountService.updateAccount(accountDto),HttpStatus.OK);
    }


}
