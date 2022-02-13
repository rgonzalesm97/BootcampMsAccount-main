package com.bank.account.repository;

import com.bank.account.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ReactiveMongoRepository<Account, String> {
}
