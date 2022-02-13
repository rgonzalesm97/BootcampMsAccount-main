package com.bank.account.controller;

import com.bank.account.entity.Account;
import com.bank.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    IAccountService service;

    @GetMapping
    public Flux<Account> getAccounts(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Account> getAccountById(@PathVariable("id") String id){
        return service.getAccountById(id);
    }

    @PostMapping
    Mono<Account> postAccount(@RequestBody Account account){
        return service.save(account);
    }

    @PutMapping
    Mono<Account> updAccount(@RequestBody Account account){
        return service.update(account);
    }

    @DeleteMapping("/{id}")
    void dltAccount(@PathVariable("id") String id){
        service.delete(id);
    }
}
