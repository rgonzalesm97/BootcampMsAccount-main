package com.bank.account.service;

import com.bank.account.entity.Account;
import com.bank.account.proxy.AccountProxy;
import com.bank.account.repository.IAccountRepository;
import com.bank.account.model.Client;

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
    	
    	switch (account.getTypeAccount()) {
		case "cuenta de ahorro":
			return createCuentaAhorro(account).flatMap(repository::save);
		case "cuenta corriente":
			return createCuentaCorriente(account).flatMap(repository::save);
		case "cuenta plazo fijo":
			return createCuentaPlazoFijo(account).flatMap(repository::save);
		default:
			return Mono.error(() -> new IllegalArgumentException("Invalid Account type"));
		}
    	
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id).subscribe();
    }
    
    @Override
	public Mono<Account> getAccountByIdClientAndTypeAccount(String idClient, String typeAccount) {
		return repository.findByIdClientAndTypeAccount(idClient, typeAccount);
	}
    
    //PRODUCT VALIDATION METHODS
    public Mono<Account> createCuentaAhorro(Account account){
    	return getClient(account).flatMap(client -> {
    		
    		switch (client.getType()) {
			case "personal":
				return Mono.just(account).flatMap(this::clientHasAccountAlready);
			case "business":
				return Mono.error(() -> new IllegalArgumentException("Business Client can't have this sccount type"));
			default:
				return Mono.error(() -> new IllegalArgumentException("Invalid Client type"));
			}
    		
    	});
	}
	
	public Mono<Account> createCuentaCorriente(Account account){
		return getClient(account).flatMap(client -> {
    		
    		switch (client.getType()) {
			case "personal":
				return Mono.just(account).flatMap(this::clientHasAccountAlready);
			case "business":
				return Mono.just(account);
			default:
				return Mono.error(() -> new IllegalArgumentException("Invalid Client type"));
			}
    		
    	});
	}
	
	public Mono<Account> createCuentaPlazoFijo(Account account){
		return getClient(account).flatMap(client -> {
    		
    		switch (client.getType()) {
			case "personal":
				return Mono.just(account).flatMap(this::clientHasAccountAlready);
			case "business":
				return Mono.error(() -> new IllegalArgumentException("Business Client can't have this sccount type"));
			default:
				return Mono.error(() -> new IllegalArgumentException("Invalid Client type"));
			}
    		
    	});
	}
    
    //PRODUCT UTIL METHODS
	public Mono<Client> getClient(Account account){
		return accountProxy.getClient(account.getIdClient());
	}
	
	public Mono<Account> clientHasAccountAlready(Account account){
		return getAccountByIdClientAndTypeAccount(account.getIdClient(), account.getTypeAccount())
				.switchIfEmpty(Mono.just(new Account()))
				.flatMap(resp -> {
					if(resp.getId()==null || resp.getId().equals(account.getId())) {
						return Mono.just(account);
					}
					return Mono.error(()->new IllegalArgumentException("Client has a this account type already"));
		});
	}

	
}
