package com.bank.account.service;

import com.bank.account.entity.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountService {

    Flux<Account> getAll();

    Mono<Account> getAccountById(String id);

    Mono<Account> save(Account account);

    Mono<Account> update(Account account);

    void delete(String id);

}
