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

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/account")
public class AccountControler {

    @Autowired
    @Qualifier("AccountServiceImpl")
    AccountService accountService;

    @Autowired
    Account account;



    @GetMapping("/findClientById")
    public ResponseEntity<Account> findClient(@RequestParam Long id) throws Exception {
        account = accountService.getAccount(id);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }


    @PostMapping("/createClient")
    public ResponseEntity<Account> createClient(@RequestBody AccountDto accountDto) throws Exception {
        account = accountService.createAccount(accountDto);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }

    @DeleteMapping("/deleteClient")
    public ResponseEntity<Long> deleteClient(@RequestParam Long id) throws Exception {
       Long idAccount = accountService.deleteAccount(id);
        return new ResponseEntity<>(idAccount,HttpStatus.OK);
    }

    @PutMapping("/updateClient")
    public ResponseEntity<Account> udateClient(@RequestBody AccountDto accountDto) throws Exception {
        account = accountService.updateAccount(accountDto);
        return new ResponseEntity<>(account,HttpStatus.OK);
    }


}
