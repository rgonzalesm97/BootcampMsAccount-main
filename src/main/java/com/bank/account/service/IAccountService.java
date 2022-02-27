package com.bank.account.service;

import com.bank.account.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountService {

    Flux<Account> getAll();
    
    Flux<Account> getAllByIdClient(String idClient);

    Mono<Account> getAccountById(String id);
    
    Mono<Account> getAccountByIdClientAndTypeAccount(String idClient, String typeAccount);

    Mono<Account> save(Account account);

    void delete(String id);

}
