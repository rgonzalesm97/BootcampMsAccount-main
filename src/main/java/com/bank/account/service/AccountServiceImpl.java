package com.bank.account.service;

import com.bank.account.entity.Account;
import com.bank.account.proxy.AccountProxy;
import com.bank.account.repository.IAccountRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import com.bank.account.model.Client;
import com.bank.account.model.DebitCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{

    
    private final IAccountRepository repository;
    
    private final AccountProxy accountProxy = new AccountProxy();

    @Override
    public Flux<Account> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Account> getAccountById(String id) {
        return repository.findById(id);
    }
    
    @Override
    public Flux<Account> getAllByIdClient(String idClient) {
    	return repository.findAllByIdClient(idClient);
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
    
    @Override
    public Mono<Account> associateWithCard(String idAccount, String idCard){
    	return getAccountById(idAccount).flatMap(resp->putCardIntoAccount(resp, idCard))
    									.flatMap(this::save);
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
	
	public Mono<DebitCard> getDebitCard(String idDebitCard){
		return accountProxy.getDebitCard(idDebitCard);
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
	
	public Mono<Account> putCardIntoAccount(Account account, String idCard){
		return cardExist(idCard).flatMap(resp->{
									if(resp.getIdClient().equals(account.getIdClient())) {
										account.setIdCard(idCard);
										return Mono.just(account);
									}else {
										return Mono.error(()->new IllegalArgumentException("This client is not the owner of this debit card"));
									}
								});
	}

	public Mono<DebitCard> cardExist(String idCard){
		return accountProxy.getDebitCard(idCard).switchIfEmpty(Mono.just(new DebitCard()))
													   .flatMap(resp -> {
														   return resp.getId()==null ? Mono.error(()->new IllegalArgumentException("Credit card doesn't exist"))
																   					 : Mono.just(resp);
													   });
	}
	
}
