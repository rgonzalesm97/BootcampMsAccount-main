package com.bank.account.repository;

import com.bank.account.entity.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveMongoRepository<Account, String> {
	public Mono<Account> findByIdClientAndTypeAccount(String idClient, String typeAccount);
	public Flux<Account> findAllByIdClient(String idClient);
}
