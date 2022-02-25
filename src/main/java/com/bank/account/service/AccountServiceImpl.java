package com.bank.account.service;

import com.bank.account.entity.Account;
import com.bank.account.model.Client;
import com.bank.account.proxy.AccountProxy;
import com.bank.account.repository.IAccountRepository;
import com.bank.account.util.AccountFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements IAccountService{

    @Autowired
    IAccountRepository repository;
    
    private AccountProxy accountProxy = new AccountProxy();

    @Override
    public Flux<Account> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Account> getAccountById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Account> save(Account account) {
    	
    	Account validAccount = AccountFactory.validateAccount(account)
    											.orElseThrow(() -> new IllegalArgumentException("Invalid Account type"));
    	return repository.save(validAccount);
//    	Mono<Client> client = accountProxy.getClient(account.getIdClient());
//    	
//    	return client.flatMap(x -> {
//    		if(x.getType().equals("personal")) {
//    			
//    			return repository.findByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount())
//								.switchIfEmpty(Mono.just(new Account()))
//								.flatMap(y -> {
//									if(y.getId()!=null) {
//										return Mono.empty();
//									}
//									return repository.save(account);
//								});
//    			
//    		}else if(x.getType().equals("business")) {
//    			if(account.getTypeAccount().equals("cuenta corriente")) {
//    				return repository.save(account);
//    			}
//    			return Mono.empty();
//    		}
//    		return Mono.empty();
//    	});
    	
    }

    @Override
    public Mono<Account> update(Account account) {
        return repository.save(account);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id).subscribe();
    }
}
